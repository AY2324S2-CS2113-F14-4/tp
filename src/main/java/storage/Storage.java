package storage;

import financialtransactions.Inflow;
import financialtransactions.Outflow;
import financialtransactions.TransactionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    public Storage(String filePath) {
        this.filePath = filePath;
        createFileDir();
    }

    public TransactionManager loadFile(String username) {
        File f = new File(filePath + String.format("/%s.txt", username));
        TransactionManager manager = new TransactionManager();
        try {
            Scanner sc = new Scanner(f);
            sc.nextLine();
            while (sc.hasNext()) {
                String[] transactionInfo = sc.nextLine().split("\\|");
                assert transactionInfo.length == 3 : "There should always be DESCRIPTION|AMOUNT|TIME.";
                double amount = Double.parseDouble(transactionInfo[1]);
                if (!transactionInfo[1].startsWith("-")) {
                    Inflow inflow = new Inflow(transactionInfo[0], amount, transactionInfo[2]);
                    inflow.setCategory(Inflow.Category.valueOf(transactionInfo[3]));
                    manager.addTransaction(inflow);
                } else {
                    Outflow outflow = new Outflow(transactionInfo[0], -amount, transactionInfo[2]);
                    outflow.setCategory(Outflow.Category.valueOf(transactionInfo[3]));
                    manager.addTransaction(outflow);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No previous transactions.");
        }
        return manager;
    }
    
    public String getPassword(String username) {
        File f = new File(filePath + String.format("/%s.txt", username));
        String pw;
        try {
            Scanner sc = new Scanner(f);
            pw =  sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("oops");
            pw = null;
        }
        return pw;
    }

    private void createFileDir() {
        File f = new File(filePath);
        f.mkdir();
    }

    public void saveFile(TransactionManager tm, String username, String password) {
        try {
            FileWriter fw = new FileWriter(filePath + String.format("/%s.txt",username));
            fw.write(password);
            fw.write("\n");
            fw.write(tm.toSave());
            fw.close();
        } catch (IOException e) {
            System.out.println("Unable to save tasks!");
        }
    }
}
