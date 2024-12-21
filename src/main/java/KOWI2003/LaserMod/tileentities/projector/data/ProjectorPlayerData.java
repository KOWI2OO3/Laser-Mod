package KOWI2003.LaserMod.tileentities.projector.data;

import com.mojang.authlib.GameProfile;

import KOWI2003.LaserMod.gui.widgets.DataProperties.SerializeProperty;
import KOWI2003.LaserMod.tileentities.projector.ProjectorWidgetTypes;
import KOWI2003.LaserMod.utils.Utils;
import KOWI2003.LaserMod.utils.Utils.GenericConsumer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public class ProjectorPlayerData extends ProjectorWidgetData {

	public GenericConsumer<GameProfile> profile;
	@SerializeProperty
	public String username = "";
	@SerializeProperty
	public boolean liveModel = false;
	@SerializeProperty
	public boolean child = false;
	
	public ProjectorPlayerData() {
		super(ProjectorWidgetTypes.Player);
		updateProfile();
	}
	
	public ProjectorPlayerData(float x, float y, float z, float size, String username) {
		this(x, y, z, size, false, false, username);
		updateProfile();
	}
	
	public void updateProfile() {
		if(profile == null)
			profile = new GenericConsumer<>();
		if(!liveModel) {
			profile = Utils.updateProfileConsumer(username, profile);
		}
	}
	
	public ProjectorPlayerData(float x, float y, float z, float size,
			boolean liveModel, boolean isChild, String username) {
		super(x, y, z, size, size, size, ProjectorWidgetTypes.Player);
		this.liveModel = liveModel;
		this.child = isChild;
		this.username = username;
		updateProfile();
	}
	
	@Override
	protected CompoundTag writeToNBT(CompoundTag tag) {
		tag.putBoolean("LiveModel", liveModel);
		tag.putBoolean("IsChild", child);
		tag.putString("Username", username);
		if(profile == null || profile.getStored() == null) {
			profile = new GenericConsumer<>();
			profile.accept(new GameProfile(null, username + "_"));
		}
		NbtUtils.writeGameProfile(tag, profile.getStored());
		return super.writeToNBT(tag);
	}
	
	@Override
	protected void readNBT(CompoundTag tag) {
		super.readNBT(tag);
		liveModel = Utils.conditionalGetBoolean("LiveModel", tag, false);
		child = Utils.conditionalGetBoolean("IsChild", tag, false);
		username = Utils.conditionalGetString("Username", tag, "");
		if(profile == null)
			profile = new GenericConsumer<>();
		profile.accept(NbtUtils.readGameProfile(tag));
	}
	
	
	
}
