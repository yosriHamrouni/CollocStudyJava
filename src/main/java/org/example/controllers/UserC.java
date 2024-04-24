package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.entities.User;

public class UserC {

    private User user= new User();
    @FXML
    private Label ban;

    @FXML
    private Label mail;

    @FXML
    private Label name;

    public void setData(User q) {
        this.user = q;
        name.setText("Name : "+q.getName()+" "+q.getPrenom());
        mail.setText("Email :"+q.getEmail());
        if(q.getIs_banned()!=0)
        {
            ban.setText("Utilisateur bloque");
        }else {
            ban.setText("Utilisateur non bloque");
        }

    }
}
