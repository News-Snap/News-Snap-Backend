package com.example.news_snap.domain.myWord.service;

import com.example.news_snap.domain.login.entity.User;
import com.example.news_snap.domain.login.repository.UserRepository;
import com.example.news_snap.domain.myWord.dto.MyWordRequest;
import com.example.news_snap.domain.myWord.dto.MyWordResponse;
import com.example.news_snap.domain.myWord.entity.MyWord;
import com.example.news_snap.domain.myWord.repository.MyWordRepository;
import com.example.news_snap.global.common.code.status.ErrorStatus;
import com.example.news_snap.global.common.exception.handler.UserHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MyWordService {

    private final MyWordRepository myWordRepository;
    private final UserRepository userRepository;


    public List<MyWordResponse.MyWordDTO> getMyWordList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserHandler(ErrorStatus._NOT_FOUND_USER));
        List<MyWord> myWordList = user.getMyWordList();
        return myWordList.stream()
                .map(a -> new MyWordResponse.MyWordDTO(a.getWord())).collect(Collectors.toList());
    }

    public MyWordResponse.addResultDTO addMyWord(Long userId, MyWordRequest request) {
        MyWord myWord = MyWord.builder()
                .word(request.word())
                .build();

        User user = userRepository.findById(userId).orElseThrow(()-> new UserHandler(ErrorStatus._NOT_FOUND_USER));
        myWord.initializeUser(user);
        myWordRepository.save(myWord);

        return MyWordResponse.addResultDTO.builder()
                .word(myWord.getWord())
                .build();
    }
}
