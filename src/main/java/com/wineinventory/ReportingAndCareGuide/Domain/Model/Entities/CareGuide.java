package com.wineinventory.ReportingAndCareGuide.Domain.Model.Entities;

import com.wineinventory.InventoryManagement.Domain.Model.ValueObjects.ImageUrl;
import com.wineinventory.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class CareGuide extends AuditableModel {


    @Column(nullable = false)
    private String accountId;

    @Column(name = "product_id")
    private Long productId;

    /**
     * The name of the care guide.
     * @guideName String
     */
    @Setter
    @Getter
    private String guideName;
    /**
     * The type of the care guide.
     * @type String
     */
    @Setter
    @Getter
    private String type;
    /**
     * The description of the care guide.
     * @description description
     */
    @Setter
    @Getter
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    })
    private ImageUrl imageUrl;

    protected CareGuide(){}

    /**
     * @summary Constructor.
     * It creates a new CareGuide instance based on the provided product, warehouse, guide name, type, and description.
     */
    /**
     * Creates a new CareGuide instance with the specified details.
     *
     * @param productId the associated product identifier (can be null)
     * @param guideName the name of the care guide (required)
     * @param type the type of the care guide (required)
     * @param description the description of the care guide (required)
     * @param imageUrl the URL of the care guide image (can be null, will use default if null or blank)
     * @throws IllegalArgumentException if guideName, type, or description is null or blank
     */
    public CareGuide(String accountId, Long productId, String guideName, String type, String description, String imageUrl) {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be null or blank");
        }
        if (guideName == null || guideName.isBlank()) {
            throw new IllegalArgumentException("Guide name cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }

        this.accountId = accountId;
        this.productId = productId;
        this.guideName = guideName;
        this.type = type;
        this.description = description;
        this.imageUrl = new ImageUrl(imageUrl);
    }
    /**
     * Updates the care guide information with the provided values.
     * Only non-null and non-empty values will be updated.
     *
     * @param guideName The new name for the care guide (optional)
     * @param type The new type/category for the care guide (optional)
     * @param description The new description/content for the care guide (optional)
     * @return The updated care guide instance
     */
    public CareGuide updateInformation(String guideName, String type, String description) {
        this.guideName = guideName;
        this.type = type;
        this.description = description;
        return this;
    }
}