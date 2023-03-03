package KOWI2003.LaserMod.tileentities;


public interface ILaserInteractable {

	public void interactWithLaser(ILaserAccess te);

	public void onLaserInteractStop(ILaserAccess te);
	
}
