package com.betterme.service;

import com.betterme.domain.dto.betterme.BetterMeOfPastResponseDto;
import com.betterme.domain.dto.betterme.BetterMeOfTodayResponseDto;
import com.betterme.domain.dto.betterme.BetterMeSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Users;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BetterMeService {

    private final BetterMeRepository betterMeRepository;
    private final UsersRepository usersRepository;

    private BetterMe findBetterMeById(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me = " + betterMeId));
    }

    private Users findUsersByUsersName(String usersName) {
        return usersRepository.findByUsersName(usersName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. users name = " + usersName));
    }

    private Users findUsersByUsersId(Long usersId) {
        return usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. users id = " + usersId));
    }

    public BetterMeSaveRequestDto getBetterMeSaveRequestDto(String usersName, LocalDate today) {
        Users users = findUsersByUsersName(usersName);

        return BetterMeSaveRequestDto.builder()
                .usersName(users.getUsersName())
                .build();
    }

    /*
        index 화면에 오늘 날짜의 BetterMe가 없을 경우 화면에 Better me 생성 버튼을 보여주기 위한 로직
    */
    public boolean hasBetterMeOfToday(String usersName, LocalDate today) {
        Users users = findUsersByUsersName(usersName);

        return hasBetterMeOfToday(users, today);
    }

    public boolean hasBetterMeOfPast(String usersName, LocalDate today) {
        Users users = findUsersByUsersName(usersName);

        return hasBetterMeOfPast(users, today);
    }

    @Transactional
    public Long save(BetterMeSaveRequestDto requestDto) {
        Users users = findUsersByUsersName(requestDto.getUsersName());
        BetterMe betterMe = requestDto.toEntity(users);

        betterMe.setHabitsAndUrl(requestDto.getHabits());

        betterMeRepository.save(betterMe);

        log.info("BetterMe is saved with users id = " + betterMe.getId());

        return betterMe.getId();
    }

    public List<BetterMeOfPastResponseDto> getBetterMeOfPastResponseDto(String usersName) {
        Users users = findUsersByUsersName(usersName);

        return getBetterMeOfPastList(users);
    }

    public BetterMeOfTodayResponseDto getBetterMeOfToday(String usersName, LocalDate today) {
        Users users = findUsersByUsersName(usersName);
        BetterMe betterMe = getBetterMeOfToday(users, today);

        return new BetterMeOfTodayResponseDto(betterMe);
    }

    @Transactional
    public void delete(Long betterMeId) {
        BetterMe betterMe = findBetterMeById(betterMeId);
        betterMeRepository.delete(betterMe);

        log.info("BetterMe is delete with better me id = " + betterMeId);
    }

    public boolean hasBetterMeOfToday(Users users, LocalDate today) {
        return users.getBetterMes().stream()
                .anyMatch(betterMe -> betterMe.getCreatedDate().toLocalDate().isEqual(today));
    }

    public boolean hasBetterMeOfPast(Users users, LocalDate today) {
        return users.getBetterMes().stream()
                .anyMatch(betterMe -> betterMe.getCreatedDate().toLocalDate().isBefore(today));
    }

    public BetterMe getBetterMeOfToday(Users users, LocalDate today) {
        return users.getBetterMes().stream()
                .filter(betterMe -> betterMe.getCreatedDate().toLocalDate().isEqual(today))
                .findFirst()
                .orElse(null);
    }

    public List<BetterMeOfPastResponseDto> getBetterMeOfPastList(Users users) {
        return users.getBetterMes().stream()
                .filter(betterMe -> betterMe.getCreatedDate().toLocalDate().isBefore(LocalDate.now()))
                .map(BetterMeOfPastResponseDto::new)
                .toList();
    }
}
