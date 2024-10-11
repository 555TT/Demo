package other.并发;

/**
 * @author: 小手WA凉
 * @create: 2024-10-09
 */
public class Account {
    private double balance;
    public void deposit(double amount) {
        this.balance += amount;
    }
    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        Account account = new Account();
        for(int i=0;i<100;i++){
            new Thread(()->{
                account.deposit(2);
            }).start();
        }
    }
}

