package com.example.gcj.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
    public static String date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDateTime = new Date();
        return dateFormat.format(currentDateTime);
    }

    public static String currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }
    public static Date expipyDate(int expiryTimeInMinute) {
        return new Date(System.currentTimeMillis() + (1000L *60*expiryTimeInMinute));
    }

    public static String generateRandomNumberString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }

    public static Date[] getStartDateAndEndDate(int year, int month) {
        Calendar startCal = Calendar.getInstance();
        startCal.set(year, month - 1, 1, 0, 0, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        Date startDate = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.set(year, month - 1, startCal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        Date endDate = endCal.getTime();

        return new Date[]{startDate, endDate};

    }

    public static long sumList(List<Long> list) {
        return list.stream().mapToLong(Long::longValue).sum();
    }

    public static List<Long> convertToList(Date startDate, Date endDate, List<Object[]> results) {
        if (results == null) {
            return new ArrayList<>();
        }
        Map<Integer, Long> countsMap = new HashMap<>();

        // Initialize counts for all days to 0
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        while (!calendar.getTime().after(endDate)) {
            countsMap.put(calendar.get(Calendar.DAY_OF_MONTH), 0L);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Populate counts from query results
        for (Object[] result : results) {
            Integer dayOfMonth = (Integer) result[0];
            Long count = (Long) result[1];
            countsMap.put(dayOfMonth, count);
        }

        // Convert map values to list
        List<Long> counts = new ArrayList<>(countsMap.values());
        return counts;
    }

    public static int getNumberOfDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        // Set the year and month
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // Calendar months are zero-based (0 - January, 1 - February, ..., 11 - December)
        // Get the actual maximum number of days in the given month and year
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfMonth(Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String generateRandomCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder code = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }

    public static Pageable generatePage(int page, int limit, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page-1, limit, direction, sortBy);
    }

    public static String generateRandomPassword() {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String allCharacters = upperCase + lowerCase + numbers;

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        // Ensure at least one upper case, lower case, and number
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));

        // Fill remaining length with random characters
        for (int i = 3; i < 8; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        // Shuffle the password characters for randomness
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }


}
