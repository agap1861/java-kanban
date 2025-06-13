package function;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @Override
    protected TaskManager createTaskManager() {
        return new InMemoryTaskManager(Managers.getDefaultHistory());
    }

}