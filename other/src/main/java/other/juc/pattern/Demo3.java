package other.juc.pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 两阶段终止模式
 * 场景：设计一个监控线程，当线程退出时，要求优雅的关闭
 @author: wanghaoran1
 @create: 2025-04-18
 */
public class Demo3 {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        TimeUnit.SECONDS.sleep(5);
        twoPhaseTermination.end();
    }
}

@Slf4j
class TwoPhaseTermination {
    Thread monitorThread;

    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("释放资源，准备结束");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("监控内存、cpu等.......");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    public void end() {
        //使用interrupt打断线程而不是stop暴力终止
        log.info("结束监控");
        monitorThread.interrupt();
    }
}
