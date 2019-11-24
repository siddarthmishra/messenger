package org.siddarth.javabrains.messenger.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.siddarth.javabrains.messenger.model.Profile;
import org.siddarth.javabrains.messenger.service.ProfileService;

@Path("profiles")
public class ProfileResource {

	private ProfileService profileService = new ProfileService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Profile> getAllProfiles() {
		return profileService.getAllProfiles();
	}

	@GET
	@Path("{profileName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Profile getProfile(@PathParam("profileName") String profileName) {
		return profileService.getProfile(profileName);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile addProfile(Profile profile) throws Exception {
		profile.setId(0);
		if (profile.getProfileName() != null && !profile.getProfileName().trim().isEmpty()) {
			profile.setProfileName(profile.getProfileName().toLowerCase().trim());
		} else {
			throw new Exception("profileName is null or empty");
		}
		profile.setCreated(new Date());
		return profileService.addProfile(profile);
	}

	@PUT
	@Path("{profileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) throws Exception {
		profile.setProfileName(profileName);
		return profileService.updateProfile(profile);
	}

	@DELETE
	@Path("{profileName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProfile(@PathParam("profileName") String profileName) throws Exception {
		Profile profile = profileService.deleteProfile(profileName);
		return "profileName " + profileName + " / ID " + profile.getId() + " deleted successfully";
	}

}
