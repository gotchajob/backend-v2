package com.example.gcj.Shared.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.gcj.Shared.util.Regex.SORT_BY;

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
        return new Date(System.currentTimeMillis() + (1000L * 60 * expiryTimeInMinute));
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
        return PageRequest.of(page - 1, limit, direction, sortBy);
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


    public static List<Sort.Order> sortConvert(String sortBy) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (sortBy == null) {
            return sorts;
        }

        if (StringUtils.hasLength(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        return sorts;
    }

    public static Pageable pageableConvert(int pageNumber, int pageSize, String sort) {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortConvert(sort)));
    }

    public static String generateCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static String[] appendStringArrays(String[] array1, String[] array2) {
        // Check for null arrays and handle accordingly
        if (array1 == null) array1 = new String[0];
        if (array2 == null) array2 = new String[0];

        // Create a new array with the combined length of both input arrays
        String[] combinedArray = new String[array1.length + array2.length];

        // Copy elements from both arrays into the combined array
        System.arraycopy(array1, 0, combinedArray, 0, array1.length);
        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);

        return combinedArray;
    }


}
