package com.team33.model.csv.Students;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hamza on 09/03/2017.
 */
public class Student {
    private String username;
    private String firstName;
    private String lastName;
    private String lastNameInMoodle;
    private String level;
    private String optin;
    private String groupe;
    private String email;
    private String password;
    private ArrayList<String> listOfEmails;
    private int positionInWorkbookOut;
    private ArrayList<String> courses;
    private ColumnsInformationBox box;
    private String key;

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student() {
        this.listOfEmails = new ArrayList<String>();
    }

    public String getLevel() {
        return level;
    }

    public String getLastNameInMoodle() {
        return lastNameInMoodle;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }
    public String toString()
    {
        return this.firstName+" "+this.lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getPositionInWorkbookOut() {
        return positionInWorkbookOut;
    }

    public void setPositionInWorkbookOut(int positionInWorkbookOut) {
        this.positionInWorkbookOut = positionInWorkbookOut;
    }
    public void setBox(ColumnsInformationBox box) {
        this.box = box;
    }

    public ArrayList getListOfEmails()
    {
        return listOfEmails;
    }

    public String getEmail() {
        return email;
    }

    public void setListOfEmails(ArrayList<String> listOfEmails) {
        this.listOfEmails = listOfEmails;
    }

    public void tryToSetEmail()
    {
        if(this.listOfEmails.size() == 1)
        {
            this.email = listOfEmails.get(0);
        }
        else
        {
            this.email = "";
        }
    }

    public boolean hasEmail()
    {
        return !this.email.equals("");
    }




    public void generateUsename()
    {
        if(!(this.email).equals("")) {
            this.username = this.email.replaceFirst(String.valueOf(email.charAt(0)), "");
            this.username = username.replace("@esi.dz", "");
        }else this.username = "";
    }

    public void createLastNameInMoodle()
    {
        if(this.optin.equals("CPI") || this.optin.equals("SC"))
        {
            this.lastNameInMoodle = this.lastName+" "+this.level+this.groupe;
        }
        else
        {
            this.lastNameInMoodle = this.lastName+" "+this.level+this.optin+this.groupe;
        }

    }



    @Override
    public int hashCode() {
        String str = this.lastName.toLowerCase().charAt(0) + this.firstName.toLowerCase();
        return str.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (!firstName.toLowerCase().equals(student.firstName.toLowerCase())) return false;
        return lastName.toLowerCase().charAt(0) == (student.lastName.toLowerCase().charAt(0));
    }

    public void rowToBasicInformations(Row row)
    {
        this.lastName = row.getCell(this.box.getColPrenom()).toString();
        this.firstName =  row.getCell(this.box.getColNom()).toString();
        this.optin = row.getCell(this.box.getColOptin()).toString();
        row.getCell(this.box.getColGroupe()).setCellType(CellType.STRING);
        this.groupe = row.getCell(this.box.getColGroupe()).toString();
        this.password = this.firstName;
    }

    public void setStudentInformations()
    {
        tryToSetEmail();
        generateUsename();
        createLastNameInMoodle();
    }

    public void allocateCourses(CourseFormat courseFormat, HashMap<String,ArrayList<String>> optionalModules)
    {
        this.courses =  new ArrayList<>();
        this.courses.addAll(courseFormat.getListOfCourses(this.level,this.optin));
        if(level.equals("2CS")) 
        {
            this.courses.addAll(optionalModules.get(this.groupe));
        }
    }


    public String getOptin() {
        return optin;
    }

    public int numberOfCourses()
    {
        return courses.size();
    }


    public String getGroupe() {
        return groupe;
    }
    public String getKey()
    {
        this.key = this.firstName.toLowerCase().replace(" ","");
        return this.key;
    }
}

