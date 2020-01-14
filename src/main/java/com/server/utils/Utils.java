package com.server.utils;

import com.server.model.enums.Role;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final String SESSION_ATTRIBUTE = "email";

    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private static final String EMAIL_PATTERN_UBB = "(.+)@(s?cs.ubbcluj.ro)";

    private static final String EMAIL_PROFESSORS = "cs.ubbcluj.ro";

    private static final String EMAIL_STUDENTS = "scs.ubbcluj.ro";


    public static Role getRoleByEmail(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        Pattern patternUBB = Pattern.compile(EMAIL_PATTERN_UBB);
        Matcher matcherUBB = patternUBB.matcher(email);

        if (matcher.matches() && matcherUBB.matches()) {
            if (matcherUBB.group(2).equalsIgnoreCase(EMAIL_PROFESSORS)) {
                return Role.PROFESSOR;
            }
            if (matcherUBB.group(2).equalsIgnoreCase(EMAIL_STUDENTS)) {
                return Role.STUDENT;
            }
        }

        return Role.NOT_SUPPORTED;
    }

    public static boolean isValid(HttpSession session) {
        try {
            return session != null && session.getAttribute(SESSION_ATTRIBUTE) != null;
        } catch (IllegalStateException e) {
        }
        return false;
    }
}
