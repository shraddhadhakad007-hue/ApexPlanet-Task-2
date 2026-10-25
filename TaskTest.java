package com.example.taskmanagerapp;
import org.junit.Test;
import static org.junit.Assert.*;
public class TaskTest {
 @Test public void newTaskStartsIncomplete() {
  Task task = new Task("Study Android", "Read layouts", false);
  assertEquals("Study Android", task.title); assertEquals("Read layouts", task.description); assertFalse(task.done);
 }
 @Test public void completedTaskHasDoneFlag() {
  Task task = new Task("Submit task", "", true); assertTrue(task.done);
 }
}
