package jp.rinad.timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {
    private final Timer plugin;
    public TimerCommand(Timer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            if (plugin.isRunning()) {
                plugin.stopTimer();
                sender.sendMessage("タイマーを停止しました");
            } else {
                sender.sendMessage("タイマーは実行中ではありません");
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            try {
                int seconds = Integer.parseInt(args[1]);
                if (seconds > 0) {
                    plugin.startTimer(seconds);
                    sender.sendMessage("タイマーを開始");
                } else {
                    sender.sendMessage("秒数は正の整数で指定してください");
                }

            } catch (NumberFormatException e) {
                sender.sendMessage("無効な秒数です");
            }
        } else {
            sender.sendMessage("使用法: /timer set <second>");
        }
        return true;
    }
}
