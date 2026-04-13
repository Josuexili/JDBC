package com.esports;

import com.esports.controller.SportsController;
import com.esports.persistence.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        try {
            new SportsController().start();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
