package com.example.SmartShop.service;

import com.example.SmartShop.constant.PredefinedRole;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.entity.Notification;
import com.example.SmartShop.entity.Role;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.enums.Type;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.StoreMapper;
import com.example.SmartShop.repository.NotificationRepository;
import com.example.SmartShop.repository.RoleRepository;
import com.example.SmartShop.repository.StoreRepository;
import com.example.SmartShop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AdminService {
    StoreRepository storeRepository;
    NotificationRepository notificationRepository;
    RoleRepository roleRepository;
    StoreMapper storeMapper;
    UserRepository userRepository;

    public List<Store> getPendingStore(){
        List<Store> stores = storeRepository.findByStatus("PENDING");
        return stores;
    }

    @Transactional
    public StoreResponse approveStore(Integer storeId, boolean isApproved, String reason){
        Store store = storeRepository.findByStoreId(storeId);
        if(store == null){
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        if (!store.getStatus().equals("PENDING")) {
            throw new RuntimeException("Store is not in PENDING status");
        }
        User user = store.getUser();
        if (isApproved) {
            store.setStatus("APPROVED");
            Role sellerRole = roleRepository.findByName(PredefinedRole.ADMIN_SELLER)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            user.setRole(sellerRole);
        } else {
            store.setStatus("REJECTED");
        }
        store.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);
        userRepository.save(user);
        String message = isApproved
                ? "Your store ' " + store.getNameStore() + " ' has been approved."
                : "Your store '" + store.getNameStore() + "' was rejected. Reason: " + reason;
        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .createdAt(LocalDate.now())
                .build();
        notificationRepository.save(notification);
        return storeMapper.toStoreResponse(store);
    }

    public void notifyAllUsers(String message){
        List<User> users = userRepository.findAll();
        List<Notification> notifications = users.stream()
                .map(user -> Notification.builder()
                        .user(user)
                        .message(message)
                        .type(Type.CUSTOMER.getDescription())
                        .createdAt(LocalDate.now())
                        .build())
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }
}
