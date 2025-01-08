import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;
public class TaskTest {

    Task task1;
    Task task2;
    Epic epic1;
    Epic epic2;
    SubTask subTask1;
    SubTask subTask2;

    @BeforeEach
    public void beforEach() {
        task1 = new Task("1","1");
        task2 = new Task("2", "2");
        epic1 = new Epic("1","1");
        epic2 = new Epic("2", "2");
        subTask1 = new SubTask("1","1");
        subTask2 = new SubTask("2","2");
    }

    @Test
    public void tasksShouldBeEqualsById() {
        task1.setId(3);
        task2.setId(3);
        assertEquals(task1,task2);
    }

    @Test
    public void epicsShouldBeEqualsById() {
        epic1.setId(1);
        epic2.setId(1);
        assertEquals(epic1,epic2);
    }

    @Test
    public void subTasksShouldBeEqualsById() {
        subTask1.setId(10);
        subTask2.setId(10);
        assertEquals(subTask1,subTask2);
    }





}
