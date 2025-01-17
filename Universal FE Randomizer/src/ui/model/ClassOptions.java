package ui.model;

public class ClassOptions {
	
	public enum BaseTransferOption {
		NO_CHANGE, ADJUST_TO_MATCH, ADJUST_TO_CLASS
	}
	
	public enum GenderRestrictionOption {
		STRICT, LOOSE, NONE
	}
	
	public enum GrowthAdjustmentOption {
		NO_CHANGE, TRANSFER_PERSONAL_GROWTHS, CLASS_RELATIVE_GROWTHS
	}
	
	public final Boolean randomizePCs;
	public final Boolean createPrfs;
	public final Boolean unbreakablePrfs;
	public final Boolean includeLords;
	public final Boolean includeThieves;
	public final Boolean includeSpecial;
	public final boolean assignEvenly;
	
	public final boolean forceChange;
	
	public final GenderRestrictionOption genderOption;
	
	public final Boolean separateMonsters; // FE8 only.
	
	public final Boolean randomizeEnemies;
	public final Boolean randomizeBosses;
	
	public final BaseTransferOption basesTransfer;
	public final GrowthAdjustmentOption growthOptions;
	
	public ClassOptions(Boolean pcs, Boolean lords, Boolean newPrfs, Boolean unbreakablePrfs, Boolean thieves, Boolean special, boolean forceChange, GenderRestrictionOption genderOption, boolean assignEvenly, Boolean enemies, Boolean bosses, BaseTransferOption basesTransfer, GrowthAdjustmentOption growthOptions) {
		super();
		randomizePCs = pcs;
		createPrfs = newPrfs;
		this.unbreakablePrfs = unbreakablePrfs;
		includeLords = lords;
		includeThieves = thieves;
		includeSpecial = special;
		this.assignEvenly = assignEvenly;
		separateMonsters = false;
		
		randomizeEnemies = enemies;
		
		randomizeBosses = bosses;
		
		this.basesTransfer = basesTransfer;
		this.growthOptions = growthOptions;
		
		this.forceChange = forceChange;
		this.genderOption = genderOption;
	}
	
	public ClassOptions(Boolean pcs, Boolean lords, Boolean newPrfs, Boolean unbreakablePrfs, Boolean thieves, Boolean special, Boolean separateMonsters, boolean forceChange, GenderRestrictionOption genderOption, boolean assignEvenly, Boolean enemies, Boolean bosses, BaseTransferOption basesTransfer, GrowthAdjustmentOption growthOptions) {
		super();
		randomizePCs = pcs;
		createPrfs = newPrfs;
		this.unbreakablePrfs = unbreakablePrfs;
		includeLords = lords;
		includeThieves = thieves;
		includeSpecial = special;
		this.assignEvenly = assignEvenly;
		this.separateMonsters = separateMonsters;
		
		randomizeEnemies = enemies;
		
		randomizeBosses = bosses;
		
		this.basesTransfer = basesTransfer;
		this.growthOptions = growthOptions;
		
		this.forceChange = forceChange;
		this.genderOption = genderOption;
	}

}
