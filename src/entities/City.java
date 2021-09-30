package entities;

// Não utiliza o hibernate pois utilizará o arquivo 'states-cities.json' para consultas
public class City {
    private Long id;
    private Long stateId;
    private String name;

    public City() {}

    public City(String name, Long id, Long stateId) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return name;
    }
}
