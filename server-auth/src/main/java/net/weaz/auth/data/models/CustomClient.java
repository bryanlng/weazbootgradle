package net.weaz.auth.data.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class CustomClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Client name is required.")
    @Column(unique = true, nullable = false)
    private String clientName;

    @NotEmpty(message = "Client secret is required.")
    private String clientSecret;

    @NotEmpty(message = "Scopes are required.")
    private String scopes;

    public CustomClient() {
    }

    public CustomClient(CustomClient customClient) {
        this.id = customClient.id;
        this.clientName = customClient.clientName;
        this.clientSecret = customClient.clientSecret;
        this.scopes = customClient.scopes;
    }

    public CustomClient(String clientName, String clientSecret, String scopes) {
        this.clientName = clientName;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }
}