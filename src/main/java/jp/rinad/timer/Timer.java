package jp.rinad.timer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

import java.awt.*;

public final class Timer extends JavaPlugin {
    private int countdownSeconds = 0;
    public boolean isRunning = false;
    private BukkitRunnable timerTask;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("timer").setExecutor(new TimerCommand(this));
    }

    public void startTimer(int seconds) {
        countdownSeconds = seconds;
        isRunning = true;
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (countdownSeconds <= 0) {
                    isRunning = false;
                    cancel();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("終了!!","時間になりました!!",10,70,20);
                        player.playSound(player.getLocation(), "block.anvil.use",1.0F,1.0F);
                    }
                } else if (countdownSeconds <= 5) {

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(),"entity.experience_orb.pickup",1.0F,1.0F);
                        player.sendTitle(String.valueOf(countdownSeconds),"",10,70,20);
                    }
                } else if (countdownSeconds <= 10) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(String.valueOf(countdownSeconds),"",10,70,20);
                    }
                }

                if (isRunning) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String msg = formatTime(countdownSeconds);
                        TextComponent component = new TextComponent();
                        component.setText(msg);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
                    }
                    countdownSeconds--;
                }
            }
        };
        timerTask.runTaskTimer(this,0,20);
    }

    public void stopTimer() {
        if (isRunning) {
            isRunning = false;
            if (timerTask != null) {
                timerTask.cancel();
            }
        }
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        if (remainingSeconds < 10) {
            // 秒が1桁の場合、0埋めで表示
            return minutes + "分 0" + remainingSeconds + "秒";
        } else {
            return minutes + "分 " + remainingSeconds + "秒";
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
