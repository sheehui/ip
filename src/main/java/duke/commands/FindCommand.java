package duke.commands;

import duke.tasks.TaskList;
import duke.util.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui) {
        StringBuilder op = new StringBuilder();

        for (int i = 0; i < tasks.getNumOfTasks(); i++) {
            if (tasks.getTask(i).descContains(keyword)) {
                op.append(tasks.getTask(i).toString()).append("\n");
            }
        }
        return "Here are the matching tasks in your list:\n" + op;
    }
}
