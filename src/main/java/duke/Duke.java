package duke;

import duke.exceptions.UserInputError;
import duke.exceptions.InvalidInputException;
import duke.tasks.Task;
import duke.tasks.TaskList;

import duke.util.Parser;
import duke.util.Ui;
import duke.util.Storage;

public class Duke {

  private static final String LINE =
    "     ____________________________________________________________\n";
//  private static final String INDENT = "      ";
  public static TaskList taskList;
  private Ui ui;

  private void run() {
    ui = new Ui();
    ui.greet();
    taskList = new TaskList(Storage.readDatabase());

    while (ui.hasInput()) {
      String input = ui.nextInput();
      Parser parser = new Parser(input);
      try {
        directInput(parser.parse());
      } catch (UserInputError e){
        Duke.renderOutput(e.getMessage());
      }
      if (ui.hasEnded()) {
        break;
      }
    }

    Storage.writeDatabase(taskList.toArrayList());
    ui.end();
  }

  public void directInput(String[] input) {
    try {
      String cmd = input[0];
      String details = input.length == 1 ? "" : input[1];
      if (cmd.equals("bye")) {
        ui.endChat();
      } else if (cmd.equals("list")) {
        ui.renderList();
      } else if (cmd.equals("done")) {
        ui.markTaskComplete(Integer.parseInt(details));
      } else if (cmd.equals("todo")) {
        ui.addNewTask(details, Task.Type.TODO);
      } else if (cmd.equals("deadline")) {
        ui.addNewTask(details, Task.Type.DEADLINE);
      } else if (cmd.equals("event")) {
        ui.addNewTask(details, Task.Type.EVENT);
      } else if (cmd.equals("delete")) {
        ui.deleteTask(Integer.parseInt(details));
      } else {
        throw new InvalidInputException();
      }
    } catch (UserInputError e) {
      Duke.renderOutput(e.getMessage());
    }
  }

//  private void renderList() {
//    StringBuilder op = new StringBuilder();
//    for (int i = 0; i < taskList.length(); i++) {
//      op
//        .append(i + 1)
//        .append(". ")
//        .append(taskList.getTask(i).toString())
//        .append("\n");
//    }
//    renderOutput("Here are the tasks in your list:\n" + op);
//  }
//
//  private void markTaskComplete(int index) throws UserInputError {
//    Task task = taskList.getTask(index);
//    if (task.isDone()) {
//      renderOutput("Great! But you have already completed this task!");
//    } else {
//      task.markDone();
//      renderOutput("Nice! I've marked this task as done: \n" + task);
//    }
//  }
//
//  private void addNewTask(String input, Task.Type type) throws UserInputError {
//      Task newTask = Task.createTask(input, type);
//      taskList.addTask(newTask);
//      addTaskOutput(newTask);
//  }
//
//  private void addTaskOutput(Task task) {
//    String output =
//      "Got it. I've added this task:\n" +
//      INDENT +
//      task.toString() +
//      "\nNow you have " +
//      taskList.length() +
//      " tasks in the list.";
//    renderOutput(output);
//  }
//
//  private void deleteTask(int index) throws UserInputError {
//    Task deleted = taskList.getTask(index);
//    taskList.deleteTask(index);
//    deleteTaskOutput(deleted);
//  }
//
//  private void deleteTaskOutput(Task task) {
//    String output =
//      "Noted. I've removed this task:\n" +
//      INDENT +
//      task.toString() +
//      "\nNow you have " +
//      taskList.length() +
//      " tasks in the list.";
//    renderOutput(output);
//  }
//
  public static void renderOutput(String op) {
    System.out.println(LINE);
    op.lines().forEach(line -> System.out.println("      " + line));
    System.out.println(LINE);
  }

  public static void main(String[] args) {
    Duke bot = new Duke();
    bot.run();
  }
}