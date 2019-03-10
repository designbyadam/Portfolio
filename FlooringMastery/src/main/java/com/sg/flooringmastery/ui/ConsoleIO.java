/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import static java.lang.Integer.min;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author afahrenkamp
 */
public class ConsoleIO implements UserIO {

    Scanner scn = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.print(msg);
    }

    @Override
    public double readDouble(String prompt) {
        double toReturn = Double.NaN;
        boolean isCorrect = false;
        while (!isCorrect) {
            String input = readString(prompt);
            try {
                toReturn = Double.parseDouble(input);
                isCorrect = true;
            } catch (NumberFormatException e) {
            }
        }
        return toReturn;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double toReturn = Double.NaN;
        boolean isCorrect = false;
        while (!isCorrect) {
            toReturn = readDouble(prompt);
            isCorrect = toReturn >= min && toReturn <= max;
        }
        return toReturn;
    }

    @Override
    public float readFloat(String prompt) {
        float toReturn = Float.NaN;
        boolean isCorrect = false;
        while (!isCorrect) {
            String input = readString(prompt);
            try {
                toReturn = Float.parseFloat(input);
                isCorrect = true;
            } catch (NumberFormatException e) {
            }
        }
        return toReturn;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float toReturn = Float.NaN;
        boolean isCorrect = false;
        while (!isCorrect) {
            toReturn = readFloat(prompt);
            isCorrect = toReturn >= min && toReturn <= max;
        }
        return toReturn;
    }

    @Override
    public int readInt(String prompt) {
        int toReturn = Integer.MIN_VALUE;
        boolean isCorrect = false;
        while (!isCorrect) {
            String input = readString(prompt);
            try {
                toReturn = Integer.parseInt(input);
                isCorrect = true;
            } catch (NumberFormatException e) {
            }
        }
        return toReturn;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int toReturn = Integer.MIN_VALUE;
        boolean isCorrect = false;
        while (!isCorrect) {
            toReturn = readInt(prompt);
            isCorrect = toReturn >= min && toReturn <= max;
        }
        return toReturn;
    }

    @Override
    public long readLong(String prompt) {
        long toReturn = Long.MIN_VALUE;
        boolean isCorrect = false;
        while (!isCorrect) {
            String input = readString(prompt);
            try {
                toReturn = Long.parseLong(input);
                isCorrect = true;
            } catch (NumberFormatException e) {
            }
        }
        return toReturn;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long toReturn = Long.MIN_VALUE;
        boolean isCorrect = false;
        while (!isCorrect) {
            toReturn = readLong(prompt);
            isCorrect = toReturn >= min && toReturn <= max;
        }
        return toReturn;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        String input = scn.nextLine();
        return input;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        BigDecimal toReturn = null;

        boolean isValid = false;
        while (!isValid) {
            String userInput = readString(prompt);
            toReturn = new BigDecimal(userInput);
            isValid = true;
        }

        return toReturn;
    }

    @Override
    public LocalDate readDate(String prompt) {
        LocalDate toReturn = null;

        boolean isValid = false;

        while (!isValid) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String userInput = readString(prompt);
            try {
                toReturn = LocalDate.parse(userInput, formatter);
                isValid = true;
            } catch (DateTimeParseException e) {

            }
        }

        return toReturn;
    }

    @Override
    public LocalDate readDate(String prompt, LocalDate min, LocalDate max) {
        LocalDate toReturn = null;

        boolean isValid = false;

        while (!isValid) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String userInput = readString(prompt);
           try { 
            toReturn = LocalDate.parse(userInput, formatter);
            isValid = toReturn.isAfter(min) && toReturn.isBefore(max);
                        } catch (DateTimeParseException e) {

            }
        }
        return toReturn;
    }

}
