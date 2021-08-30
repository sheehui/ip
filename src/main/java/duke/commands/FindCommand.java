package duke.commands;

import duke.exceptions.UserInputError;
import duke.tasks.TaskList;
import duke.util.Storage;
import duke.util.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws UserInputError {
        StringBuilder op = new StringBuilder();

        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getTask(i).descContains(keyword)) {
                op.append(tasks.getTask(i).toString()).append("\n");
            }
        }
        return ui.formatOutput("Here are the matching tasks in your list:\n" + op);
    }
}