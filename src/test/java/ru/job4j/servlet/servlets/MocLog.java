package ru.job4j.servlet.servlets;

public class MocLog {

    public void error(String massage) {
        this.error(massage, null);
    }

    public void error(String massage, Exception e) {
        System.out.println(massage);
        if (e != null) {
            e.printStackTrace();
        }
    }
}
