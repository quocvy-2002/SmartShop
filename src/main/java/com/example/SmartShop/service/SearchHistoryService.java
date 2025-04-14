package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateSearchHistory;
import com.example.SmartShop.dto.response.SearchHistoryResponse;
import com.example.SmartShop.entity.SeachHistory;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.SearchHistoryMapper;
import com.example.SmartShop.repository.SearchHistoryRepository;
import com.example.SmartShop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class SearchHistoryService {
    SearchHistoryRepository searchHistoryRepository;
    SearchHistoryMapper searchHistoryMapper;
    UserRepository userRepository;

    public SearchHistoryResponse creatQuery(CreateSearchHistory request){
        var context = SecurityContextHolder.getContext();
        var userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        SeachHistory seachHistory = SeachHistory.builder()
                .query(request.getQuery())
                .user(user)
                .createdAt(LocalDate.now())
                .processedQuery(null)
                .build();
        return searchHistoryMapper.toSearchHistoryResponse(searchHistoryRepository.save(seachHistory));

    }

    public List<SeachHistory> getSearchHistory(){
        var context = SecurityContextHolder.getContext();
        var userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<SeachHistory> listSearch = searchHistoryRepository.findByUser_UserName(user.getUserName());
        return listSearch;
    }

    public void deleteSearchHistory(Integer searchId){
        searchHistoryRepository.deleteById(searchId);
        log.info("Search History Deleted");
    }
    public void clearAllSearchHistory() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        searchHistoryRepository.deleteAllByUser(user);
    }
}
