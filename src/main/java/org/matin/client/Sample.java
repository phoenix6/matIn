package org.matin.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * The Sample class provides an interface for reading and writing material 
 * sample objects to the matIN database. 
 * 
 * Samples represent organizational structures in the matIN database. They 
 * usually represent either a physical material specimen that has been 
 * experimentally measured or a simulated dataset that is a model for an 
 * actual material system. Samples may have any number of data objects that
 * represent the results of these experiments or simulations. Generally, these
 * data objects capture some aspect of the structure of the sample's material.
 * 
 * @author Dave Turner
 *
 */
public class Sample extends MatINWriteableObject {

	public Sample() { }
	
	/**
	 *  Create a Sample object with the bare minimum of required information. Please 
	 *  add more with the appropriate setter methods.
	 * 
	 * @param name A name for this sample.
	 * @param material The material of the sample.
	 */
	public Sample(String name, Material material) {
		super();
		this.name = name;
		this.material = material;
	}

	/**
	 * Get the the name of this Sample object.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this sample object.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get a text description of this sample object.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the text the description of this sample object.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the owner of the sample object. This is whoever created it.
	 * 
	 * @return the owner's user name
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Get the material object that this sample is made of.
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Set the material object that this sample is made of.
	 * 
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Add an image of the sample to it's profile.
	 * 
	 * @param imageFilePath The location of the image file to add.
	 */
	public void addSampleImageFile(String imageFilePath) throws IOException
	{
		// Try to open the image to check if it is valid
		BufferedImage img = ImageIO.read(new File(imageFilePath));

		// If we didn't get an exception then we can add this valid image to the
		// list.
		imageURLs.add(imageFilePath);
	}

	/**
	 * Remove a specific image file from the profile of a sample.
	 * 
	 * @param imageFilePath
	 */
	public void removeSampleImageFile(String imageFilePath)
	{
		imageURLs.remove(imageFilePath);
	}

	/**
	 * @return the imageURLs
	 */
	public List<String> getImageURLs() {
		return imageURLs;
	}

	/**
	 * @param imageURLs the imageURLs to set
	 */
	public void setImageURLs(List<String> imageURLs) {
		this.imageURLs = imageURLs;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	/**
	 * @param referenceURLs the referenceURLs to set
	 */
	public void setReferenceURLs(List<String> referenceURLs) {
		this.referenceURLs = referenceURLs;
	}

	/**
	 * Get the search keywords for this material.
	 * 
	 * @return The search keywords for this material.
	 */
	public List<String> getKeywords() { return keywords; }
	
	/**
	 * Get the reference URLs for this material.
	 * 
	 * @return The reference URLs for this material.
	 */
	public List<String> getReferenceURLs() { return referenceURLs; }

	protected String name;

	protected String description; 

	protected String owner;
	
	protected Material material;

	protected List<String> imageURLs = new ArrayList<String>();

	protected List<String> keywords = new ArrayList<String>();
	
	protected List<String> referenceURLs = new ArrayList<String>();
	
}
