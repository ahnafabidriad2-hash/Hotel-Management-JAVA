public abstract class Person {
    protected String id;
    protected String name;
    protected String mobile;

   
    public Person(String id, String name, String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    
    public abstract void addPerson();

    
    public abstract void deletePerson();

    
    public abstract void showDetails();

    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

   
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
