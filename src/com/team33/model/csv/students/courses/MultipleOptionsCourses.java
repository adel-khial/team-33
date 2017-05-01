package com.team33.model.csv.students.courses;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hamza on 19/04/2017.
 */
public class MultipleOptionsCourses implements Serializable{
    private HashMap<String,HashSet<Course>> semestre1 = new HashMap<>();
    private HashMap<String,HashSet<Course>> semestre2 = new HashMap<>();
    private ArrayList<String> options;
    private HashSet<Course> modulesCommuns = new HashSet<>();
    private HashSet<Course> modulesOptionnels = new HashSet<>();


    public void ajouterCoursSemestre1(String option,String shortName,String fullName) throws UnExistingOptionException {
        if(this.semestre1 == null) this.semestre1 = new HashMap<>();
        if(options.contains(option))
        {
        Course course = new Course(shortName,fullName);
        if(!this.semestre1.containsKey(option)) this.semestre1.put(option,new HashSet<>());
        this.semestre1.get(option).add(course);
        }else {
            throw new UnExistingOptionException("Cette option n'existe pas");
        }
    }

    public void ajouterCoursSemestre2(String option,String shortName,String fullName) throws UnExistingOptionException{
        if(this.semestre2 == null) this.semestre2 = new HashMap<>();
        if(options.contains(option))
        {
            Course course = new Course(shortName,fullName);
            if(!this.semestre2.containsKey(option)) this.semestre2.put(option,new HashSet<>());
            this.semestre2.get(option).add(course);
        }else {
            throw new UnExistingOptionException("Cette option n'existe pas");
        }
    }

    public void supprimerCoursSemestre1(String option,String shortName,String fullName){
        this.semestre1.get(option).remove(new Course(shortName,fullName));
    }

    public void supprimerCoursSemestre2(String option,String shortName,String fullName){
        this.semestre2.get(option).remove(new Course(shortName,fullName));
    }

    public void ajouterModuleCommun(String shortName, String fullName){
        Course course = new Course(shortName,fullName);
        if(this.modulesCommuns == null) this.modulesCommuns = new HashSet<>();
        this.modulesCommuns.add(course);
    }

    public void ajouterModuleOptionnele(String shortName, String fullName){
        Course course = new Course(shortName,fullName);
        if(this.modulesOptionnels == null) this.modulesOptionnels = new HashSet<>();
        this.modulesOptionnels.add(course);
    }

    public void suprimerModulesCommun(String shortName,String fullName){
        this.modulesCommuns.remove(new Course(shortName,fullName));
    }

    public void supprimerModulesOptionnel(String shortName,String fullName){
        this.modulesOptionnels.remove(new Course(shortName,fullName));
    }

    public HashSet<Course> getModulesCommuns() {
        if(modulesCommuns == null )return new HashSet<>();
        else return modulesCommuns;
    }

    public HashSet<Course> getSemestre1(String option){
        if(semestre1.containsKey(option)) return semestre1.get(option);
        else return new HashSet<>();
    }

    public HashSet<Course> getSemestre2(String option){
        if(semestre2.containsKey(option)) return semestre2.get(option);
        else return new HashSet<>();
    }

    public HashSet<Course> getModulesOptionnels(){
        if(modulesOptionnels == null )return new HashSet<>();
        else return modulesOptionnels;
    }


    public void ajouterOption(String option){
        if(this.options == null) this.options = new ArrayList<>();
        this.options.add(option);
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
