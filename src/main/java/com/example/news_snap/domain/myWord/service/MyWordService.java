package com.example.news_snap.domain.myWord.service;

import com.example.news_snap.domain.myWord.entity.MyWord;
import com.example.news_snap.domain.myWord.repository.MyWordRepository;
import com.example.news_snap.domain.user.entity.User;
import com.example.news_snap.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyWordService {

    private final MyWordRepository myWordRepository;
    private final UserRepository userRepository;


    public List<MyWord> findMyWordList(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<MyWord> myWordList = user.getMyWordList();
        return myWordList;


    }




    public void addMyWord(Long userId, MyWord myWord) {

        User user = userRepository.findById(userId).orElseThrow();  //예외처리
        myWord.setUser(user);
        myWordRepository.save(myWord);
    }


}
