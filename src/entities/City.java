package entities;

// Não utiliza o hibernate pois utilizará o arquivo 'states-cities.json' para consultas
public class City {
    private int id;
    private Long stateId;
    private String name;

    public City() {}

    public City(String name, int id, Long stateId) {
        this.name = name;
        this.id = id;
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}
