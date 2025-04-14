package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateStoreRequest;
import com.example.SmartShop.dto.request.UpdateStoreRequest;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.enums.Status;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.StoreMapper;
import com.example.SmartShop.repository.StoreRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class StoreService {
    StoreRepository storeRepository;
    StoreMapper storeMapper;
    UserRepository userRepository;

    public StoreResponse getMyStore(){
        var context = SecurityContextHolder.getContext();
        var storeName = context.getAuthentication().getName();
        Store store = storeRepository.findByNameStore(storeName)
                .orElseThrow(() -> new AppException(ErrorCode.STORE_NOT_EXISTED));
        return storeMapper.toStoreResponse(store);
    }

    public StoreResponse createStore(CreateStoreRequest request) {
        var context = SecurityContextHolder.getContext();
        var userName = context.getAuthentication().getName();
        if(!userName.equals(request.getUserName())){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Optional<User> user = userRepository.findByUserName(request.getUserName());
        if(!user.isPresent()){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (user.get().getStore() != null) {
            throw new AppException(ErrorCode.STORE_EXISTED);
        }
        Store store = storeMapper.toStore(request);
        store.setUser(user.get());
        store.setStatus(Status.PENDING.name());
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());
        return storeMapper.toStoreResponse(storeRepository.save(store));
    }

    // chỉ adminn mới truy cập được phương thức này
    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores;
    }

    public StoreResponse getStoreById(Integer storeId) {
        Store store = storeRepository.findByStoreId(storeId);
        if(store == null){
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        return storeMapper.toStoreResponse(store);
    }

    public void inactiveStore(Integer storeId){
        Store store = storeRepository.findByStoreId(storeId);
        if(store == null){
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        store.setStatus(Status.INACTIVE.name());
        store.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);
    }

    public void activeStore(Integer storeId){
        Store store = storeRepository.findByStoreId(storeId);
        if(store == null){
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);

        }
        store.setStatus(Status.PENDING.name());
        store.setUpdatedAt(LocalDateTime.now());
        storeRepository.save(store);
    }

    public StoreResponse updateStore(Integer storeId ,UpdateStoreRequest request) {
        Store store = storeRepository.findByStoreId(storeId);
        if(store == null){
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        store.setUpdatedAt(LocalDateTime.now());
        storeMapper.updateStore(store, request);
        return storeMapper.toStoreResponse(storeRepository.save(store));
    }

}
