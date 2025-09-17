package pojo;

import java.util.ArrayList;
import java.util.List;

public class PostRequestBody {

    private String name;
    private String job;

    List<String> languages;

    List<City> cities = new ArrayList<>();

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public PostRequestBody() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public List<String> getLanguages() {
        return languages;
    }

}
