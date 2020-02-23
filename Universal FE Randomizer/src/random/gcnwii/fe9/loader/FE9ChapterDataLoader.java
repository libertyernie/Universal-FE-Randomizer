package random.gcnwii.fe9.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fedata.gcnwii.fe9.FE9Base64;
import fedata.gcnwii.fe9.FE9ChapterArmy;
import fedata.gcnwii.fe9.FE9ChapterRewards;
import fedata.gcnwii.fe9.FE9ChapterUnit;
import fedata.gcnwii.fe9.FE9Character;
import fedata.gcnwii.fe9.FE9Class;
import fedata.gcnwii.fe9.FE9Data;
import fedata.gcnwii.fe9.FE9Item;
import fedata.gcnwii.fe9.FE9Skill;
import io.gcn.GCNCMBFileHandler;
import io.gcn.GCNDataFileHandler;
import io.gcn.GCNFileHandler;
import io.gcn.GCNISOException;
import io.gcn.GCNISOHandler;
import util.DebugPrinter;
import util.recordkeeper.Base64Asset;
import util.recordkeeper.ChangelogAsset;
import util.recordkeeper.ChangelogBuilder;
import util.recordkeeper.ChangelogHeader;
import util.recordkeeper.ChangelogHeader.HeaderLevel;
import util.recordkeeper.ChangelogText.Style;
import util.recordkeeper.ChangelogSection;
import util.recordkeeper.ChangelogStyleRule;
import util.recordkeeper.ChangelogTOC;
import util.recordkeeper.ChangelogTable;
import util.recordkeeper.ChangelogText;

public class FE9ChapterDataLoader {
	
	List<FE9ChapterArmy> allChapterArmies;
	Map<FE9Data.Chapter, List<FE9ChapterArmy>> armiesByChapter;
	
	List<FE9ChapterRewards> allChapterRewards;
	Map<FE9Data.Chapter, FE9ChapterRewards> rewardsByChapter;
	
	public FE9ChapterDataLoader(GCNISOHandler isoHandler, FE9CommonTextLoader commonTextLoader) throws GCNISOException {
		allChapterArmies = new ArrayList<FE9ChapterArmy>();
		armiesByChapter = new HashMap<FE9Data.Chapter, List<FE9ChapterArmy>>();
		
		allChapterRewards = new ArrayList<FE9ChapterRewards>();
		rewardsByChapter = new HashMap<FE9Data.Chapter, FE9ChapterRewards>();
		
		for (FE9Data.Chapter chapter : FE9Data.Chapter.values()) {
			List<FE9ChapterArmy> armyList = new ArrayList<FE9ChapterArmy>();
			for (String difficultyPath : chapter.getAllDifficulties()) {
				GCNFileHandler handler = isoHandler.handlerForFileWithName(difficultyPath);
				assert(handler instanceof GCNDataFileHandler);
				if (!(handler instanceof GCNDataFileHandler)) { continue; }
				GCNDataFileHandler dataFileHandler = (GCNDataFileHandler)handler;
				FE9ChapterArmy army = new FE9ChapterArmy(dataFileHandler, chapter, difficultyPath);
				allChapterArmies.add(army);
				armyList.add(army);
			}
			armiesByChapter.put(chapter, armyList);
			
			if (chapter.getScriptPath() != null) {
				GCNFileHandler handler = isoHandler.handlerForFileWithName(chapter.getScriptPath());
				assert(handler instanceof GCNCMBFileHandler);
				if (!(handler instanceof GCNCMBFileHandler)) { continue; }
				GCNCMBFileHandler cmbFileHandler = (GCNCMBFileHandler)handler;
				FE9ChapterRewards rewards = new FE9ChapterRewards(cmbFileHandler);
				allChapterRewards.add(rewards);
				rewardsByChapter.put(chapter, rewards);
			}
		}
	}
	
	public FE9ChapterRewards rewardsForChapter(FE9Data.Chapter chapter) {
		return rewardsByChapter.get(chapter);
	}
	
	public List<FE9ChapterRewards> getAllChapterRewards() {
		return allChapterRewards;
	}

	public void debugPrintAllChapterArmies() {
		for (FE9ChapterArmy army : allChapterArmies) {
			DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "===== Printing Chapter Army: " + army.getID() + " ======");
			for (String unitID : army.getAllUnitIDs()) {
				FE9ChapterUnit unit = army.getUnitForUnitID(unitID);
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "--- Starting Character " + unitID + " ---");
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "PID: " + army.getPIDForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "JID: " + army.getJIDForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Weapon 1: " + army.getWeapon1ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Weapon 2: " + army.getWeapon2ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Weapon 3: " + army.getWeapon3ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Weapon 4: " + army.getWeapon4ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Item 1: " + army.getItem1ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Item 2: " + army.getItem2ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Item 3: " + army.getItem3ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Item 4: " + army.getItem4ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Skill 1: " + army.getSkill1ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Skill 2: " + army.getSkill2ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Skill 3: " + army.getSkill3ForUnit(unit));
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Starting Coordinates: (" + army.getStartingXForUnit(unit) + ", " + army.getStartingYForUnit(unit) + ")");
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "Ending Coordinates: (" + army.getEndingXForUnit(unit) + ", " + army.getEndingYForUnit(unit) + ")");
				DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "--- Ending Character " + unitID + " ---");
			}
			DebugPrinter.log(DebugPrinter.Key.FE9_CHAPTER_LOADER, "===== End Chapter Army: " + army.getID() + " =====");
		}
	}
	
	public List<FE9ChapterArmy> armiesForChapter(FE9Data.Chapter chapter) {
		return armiesByChapter.get(chapter);
	}
	
	public void commitChanges() {
		for (FE9ChapterArmy army : allChapterArmies) {
			army.commitChanges();
		}
	}
	
	public void recordOriginalChapterData(ChangelogBuilder builder, ChangelogSection chapterDataSection,
			FE9CommonTextLoader textData, FE9CharacterDataLoader charData, FE9ClassDataLoader classData, FE9SkillDataLoader skillData, FE9ItemDataLoader itemData) {
		
		chapterDataSection.addElement(new ChangelogHeader(HeaderLevel.HEADING_2, "Chapter Army Data", "chapter-army-data-header"));
		
		ChangelogTOC armyTOC = new ChangelogTOC("chapter-army-data-toc");
		armyTOC.addClass("chapter-toc");
		chapterDataSection.addElement(armyTOC);
		
		ChangelogSection mainContainer = new ChangelogSection("chapter-army-main");
		chapterDataSection.addElement(mainContainer);
		
		for (FE9Data.Chapter chapter : FE9Data.Chapter.allChapters()) {
			createChapterArmySection(chapter, charData, classData, skillData, itemData, textData, mainContainer, armyTOC, true);
		}
		
		setupRules(builder);
	}
	
	public void recordUpdatedChapterData(ChangelogSection chapterDataSection,
			FE9CommonTextLoader textData, FE9CharacterDataLoader charData, FE9ClassDataLoader classData, FE9SkillDataLoader skillData, FE9ItemDataLoader itemData) {
		ChangelogTOC armyTOC = (ChangelogTOC)chapterDataSection.getChildWithIdentifier("chapter-army-data-toc");
		ChangelogSection mainContainer = (ChangelogSection)chapterDataSection.getChildWithIdentifier("chapter-army-main");
		for (FE9Data.Chapter chapter : FE9Data.Chapter.allChapters()) {
			createChapterArmySection(chapter, charData, classData, skillData, itemData, textData, mainContainer, armyTOC, false);
		}
	}
	
	private void setupRules(ChangelogBuilder builder) {
		ChangelogStyleRule mainContainerRule = new ChangelogStyleRule();
		mainContainerRule.setElementIdentifier("chapter-army-main");
		mainContainerRule.addRule("display", "flex");
		mainContainerRule.addRule("flex-direction", "column");
		builder.addStyle(mainContainerRule);
		
		ChangelogStyleRule chapterSectionRule = new ChangelogStyleRule();
		chapterSectionRule.setElementClass("chapter-army-section");
		chapterSectionRule.addRule("display", "flex");
		chapterSectionRule.addRule("flex-direction", "row");
		builder.addStyle(chapterSectionRule);
		
		ChangelogStyleRule difficultySectionRule = new ChangelogStyleRule();
		difficultySectionRule.setElementClass("chapter-army-difficulty-section");
		difficultySectionRule.addRule("flex", "0 0 20%");
		difficultySectionRule.addRule("margin", "10px");
		builder.addStyle(difficultySectionRule);
		
		ChangelogStyleRule tablesRule = new ChangelogStyleRule();
		tablesRule.setElementClass("chapter-army-difficulty-section");
		tablesRule.setChildTags(new ArrayList<String>(Arrays.asList("table", "th", "td")));
		tablesRule.addRule("border", "1px solid black");
		builder.addStyle(tablesRule);
	}
	
	private void createChapterArmySection(FE9Data.Chapter chapter, FE9CharacterDataLoader charData, FE9ClassDataLoader classData, FE9SkillDataLoader skillData,
			FE9ItemDataLoader itemData, FE9CommonTextLoader textData, ChangelogSection mainContainer, ChangelogTOC toc, boolean isOriginal) {
		ChangelogSection chapterSection;
		
		ChangelogSection crossSection;
		ChangelogSection normalSection;
		ChangelogSection hardSection;
		ChangelogSection easySection;
		
		if (isOriginal) {
			mainContainer.addElement(new ChangelogHeader(HeaderLevel.HEADING_3, chapter.getDisplayString(), chapter.toString() + "-header"));
			
			chapterSection = new ChangelogSection("chapter-section-" + chapter.toString());
			chapterSection.addClass("chapter-army-section");
			mainContainer.addElement(chapterSection);
			
			toc.addAnchorWithTitle(chapter.toString() + "-header", chapter.getDisplayString());
			
			crossSection = new ChangelogSection("chapter-section-cross-" + chapter.toString());
			crossSection.addClass("chapter-army-difficulty-section");
			ChangelogHeader crossHeader = new ChangelogHeader(HeaderLevel.HEADING_4, "dispos_c (Cross Difficulty)", chapter.toString() + "-cross-header");
			crossHeader.addClass("chapter-army-difficulty-header");
			crossSection.addElement(crossHeader);
			normalSection = new ChangelogSection("chapter-section-normal-" + chapter.toString());
			normalSection.addClass("chapter-army-difficulty-section");
			ChangelogHeader normalHeader = new ChangelogHeader(HeaderLevel.HEADING_4, "dispos_n (Normal Difficulty)", chapter.toString() + "-normal-header");
			normalHeader.addClass("chapter-army-difficulty-header");
			normalSection.addElement(normalHeader);
			hardSection = new ChangelogSection("chapter-section-hard-" + chapter.toString());
			hardSection.addClass("chapter-army-difficulty-section");
			ChangelogHeader hardHeader = new ChangelogHeader(HeaderLevel.HEADING_4, "dispos_h (Hard Difficulty)", chapter.toString() + "-hard-header");
			hardHeader.addClass("chapter-army-difficulty-header");
			hardSection.addElement(hardHeader);
			easySection = new ChangelogSection("chapter-section-easy-" + chapter.toString());
			easySection.addClass("chapter-army-difficulty-section");
			ChangelogHeader easyHeader = new ChangelogHeader(HeaderLevel.HEADING_4, "dispos_m (Easy Difficulty)", chapter.toString() + "-easy-header");
			easyHeader.addClass("chapter-army-difficulty-header");
			easySection.addElement(easyHeader);
			
			chapterSection.addElement(crossSection);
			chapterSection.addElement(easySection);
			chapterSection.addElement(normalSection);
			chapterSection.addElement(hardSection);
		} else {
			chapterSection = (ChangelogSection)mainContainer.getChildWithIdentifier("chapter-section-" + chapter.toString());
			
			crossSection = (ChangelogSection)chapterSection.getChildWithIdentifier("chapter-section-cross-" + chapter.toString());
			normalSection = (ChangelogSection)chapterSection.getChildWithIdentifier("chapter-section-normal-" + chapter.toString());
			hardSection = (ChangelogSection)chapterSection.getChildWithIdentifier("chapter-section-hard-" + chapter.toString());
			easySection = (ChangelogSection)chapterSection.getChildWithIdentifier("chapter-section-easy-" + chapter.toString());
		}
		
		for (FE9ChapterArmy army : armiesForChapter(chapter)) {
			if (army.getID().contains("dispos_c")) {
				// Cross Difficulty
				createChapterArmy(army, charData, classData, skillData, itemData, textData, crossSection, isOriginal, chapter.toString() + "-cross");
			}
			if (army.getID().contains("dispos_n")) {
				// Normal Difficulty
				createChapterArmy(army, charData, classData, skillData, itemData, textData, normalSection, isOriginal, chapter.toString() + "-normal");
			}
			if (army.getID().contains("dispos_h")) {
				// Hard Difficulty
				createChapterArmy(army, charData, classData, skillData, itemData, textData, hardSection, isOriginal, chapter.toString() + "-hard");
			}
			if (army.getID().contains("dispos_m")) {
				// Easy Difficulty
				createChapterArmy(army, charData, classData, skillData, itemData, textData, easySection, isOriginal, chapter.toString() + "-easy");
			}
		}
	}
	
	private void createChapterArmy(FE9ChapterArmy army, FE9CharacterDataLoader charData, FE9ClassDataLoader classData, FE9SkillDataLoader skillData,
			FE9ItemDataLoader itemData, FE9CommonTextLoader textData, ChangelogSection difficultySection, boolean isOriginal, String identifier) {
		ChangelogTable unitTable;
		if (isOriginal) {
			unitTable = new ChangelogTable(3, new String[] {"Unit Number", "Old Value", "New Value"}, identifier);
			unitTable.addClass("chapter-army-data-table");
			difficultySection.addElement(unitTable);
		} else {
			unitTable = (ChangelogTable)difficultySection.getChildWithIdentifier(identifier);
		}
		
		int counter = 0;
		for (String unitID : army.getAllUnitIDs()) {
			FE9ChapterUnit unit = army.getUnitForUnitID(unitID);
			if (isOriginal) {
				unitTable.addRow(new String[] {"Unit #" + (counter + 1), "", ""});
			}
			unitTable.setElement(counter, (isOriginal ? 1 : 2), buildChangelogSectionForUnit(army, unit, charData, classData, skillData, itemData, textData, unitID + "-" + identifier + (isOriginal ? "-original" : "-new")));
			counter++;
		}
	}
	
	private ChangelogSection buildChangelogSectionForUnit(FE9ChapterArmy army, FE9ChapterUnit unit, FE9CharacterDataLoader charData, FE9ClassDataLoader classData, 
			FE9SkillDataLoader skillData, FE9ItemDataLoader itemData, FE9CommonTextLoader textData, String identifier) {
		ChangelogSection section = new ChangelogSection(identifier);
		section.addClass("chapter-army-unit-section");
		
		String pid = army.getPIDForUnit(unit);
		FE9Character character = charData.characterWithID(pid);
		if (charData.isPlayableCharacter(character) || charData.isBossCharacter(character)) {
			section.addElement(new ChangelogText(identifier + "-pid", Style.BOLD, textData.textStringForIdentifier(charData.getMPIDForCharacter(character))));
		} else {
			section.addElement(new ChangelogText(identifier + "-pid", Style.BOLD, pid));
		}
		
		String jid = army.getJIDForUnit(unit);
		FE9Class charClass = classData.classWithID(jid);
		if (charClass != null) {
			section.addElement(new ChangelogText(identifier + "-jid", Style.NONE, textData.textStringForIdentifier(classData.getMJIDForClass(charClass))));
		} else {
			section.addElement(new ChangelogText(identifier + "-jid", Style.NONE, jid));
		}
		
		String iid1 = army.getWeapon1ForUnit(unit);
		String iid2 = army.getWeapon2ForUnit(unit);
		String iid3 = army.getWeapon3ForUnit(unit);
		String iid4 = army.getWeapon4ForUnit(unit);
		
		if (iid1 != null) {
			FE9Item weapon = itemData.itemWithIID(iid1);
			if (weapon != null) {
				section.addElement(new ChangelogText(identifier + "-iid1", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(weapon))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid1", Style.NONE, iid1));
			}
		}
		if (iid2 != null) {
			FE9Item weapon = itemData.itemWithIID(iid2);
			if (weapon != null) {
				section.addElement(new ChangelogText(identifier + "-iid2", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(weapon))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid2", Style.NONE, iid2));
			}
		}
		if (iid3 != null) {
			FE9Item weapon = itemData.itemWithIID(iid3);
			if (weapon != null) {
				section.addElement(new ChangelogText(identifier + "-iid3", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(weapon))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid3", Style.NONE, iid3));
			}
		}
		if (iid4 != null) {
			FE9Item weapon = itemData.itemWithIID(iid4);
			if (weapon != null) {
				section.addElement(new ChangelogText(identifier + "-iid4", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(weapon))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid4", Style.NONE, iid4));
			}
		}
		
		iid1 = army.getItem1ForUnit(unit);
		iid2 = army.getItem2ForUnit(unit);
		iid3 = army.getItem3ForUnit(unit);
		iid4 = army.getItem4ForUnit(unit);
		
		if (iid1 != null) {
			FE9Item equipment = itemData.itemWithIID(iid1);
			if (equipment != null) {
				section.addElement(new ChangelogText(identifier + "-iid1e", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(equipment))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid1e", Style.NONE, iid1));
			}
		}
		if (iid2 != null) {
			FE9Item equipment = itemData.itemWithIID(iid2);
			if (equipment != null) {
				section.addElement(new ChangelogText(identifier + "-iid2e", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(equipment))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid2e", Style.NONE, iid2));
			}
		}
		if (iid3 != null) {
			FE9Item equipment = itemData.itemWithIID(iid3);
			if (equipment != null) {
				section.addElement(new ChangelogText(identifier + "-iid3e", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(equipment))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid3e", Style.NONE, iid3));
			}
		}
		if (iid4 != null) {
			FE9Item equipment = itemData.itemWithIID(iid4);
			if (equipment != null) {
				section.addElement(new ChangelogText(identifier + "-iid4e", Style.NONE, textData.textStringForIdentifier(itemData.getMIIDOfItem(equipment))));
			} else {
				section.addElement(new ChangelogText(identifier + "-iid4e", Style.NONE, iid4));
			}
		}
		
		String sid1 = army.getSkill1ForUnit(unit);
		String sid2 = army.getSkill2ForUnit(unit);
		String sid3 = army.getSkill3ForUnit(unit);
		
		if (sid1 != null) {
			FE9Skill skill = skillData.getSkillWithSID(sid1);
			if (skill != null) {
				ChangelogSection skillSection = new ChangelogSection("chapter-army-unit-" + identifier + "-sid1");
				skillSection.addClass("chapter-army-unit-skill-section");
				String base64 = FE9Base64.base64StringForSkill(FE9Data.Skill.withSID(sid1));
				if (base64 != null) {
					Base64Asset asset = new Base64Asset("skill-" + sid1, FE9Base64.skillBase64Prefix + base64, 32, 32);
					if (asset != null) { skillSection.addElement(new ChangelogAsset("chapter-army-unit-" + identifier + "-sid1-skill", asset)); }
				}
				skillSection.addElement(new ChangelogText(identifier + "-sid1", Style.NONE, textData.textStringForIdentifier(skillData.getMSID(skill)) + " (" + sid1 + ")"));
				section.addElement(skillSection);
			} else {
				section.addElement(new ChangelogText(identifier + "-sid1", Style.NONE, sid1));
			}
		}
		if (sid2 != null) {
			FE9Skill skill = skillData.getSkillWithSID(sid2);
			if (skill != null) {
				ChangelogSection skillSection = new ChangelogSection("chapter-army-unit-" + identifier + "-sid2");
				skillSection.addClass("chapter-army-unit-skill-section");
				String base64 = FE9Base64.base64StringForSkill(FE9Data.Skill.withSID(sid2));
				if (base64 != null) {
					Base64Asset asset = new Base64Asset("skill-" + sid2, FE9Base64.skillBase64Prefix + base64, 32, 32);
					if (asset != null) { skillSection.addElement(new ChangelogAsset("chapter-army-unit-" + identifier + "-sid2-skill", asset)); }
				}
				skillSection.addElement(new ChangelogText(identifier + "-sid2", Style.NONE, textData.textStringForIdentifier(skillData.getMSID(skill)) + " (" + sid2 + ")"));
				section.addElement(skillSection);
			} else {
				section.addElement(new ChangelogText(identifier + "-sid2", Style.NONE, sid2));
			}
		}
		if (sid3 != null) {
			FE9Skill skill = skillData.getSkillWithSID(sid3);
			if (skill != null) {
				ChangelogSection skillSection = new ChangelogSection("chapter-army-unit-" + identifier + "-sid3");
				skillSection.addClass("chapter-army-unit-skill-section");
				String base64 = FE9Base64.base64StringForSkill(FE9Data.Skill.withSID(sid3));
				if (base64 != null) {
					Base64Asset asset = new Base64Asset("skill-" + sid3, FE9Base64.skillBase64Prefix + base64, 32, 32);
					if (asset != null) { skillSection.addElement(new ChangelogAsset("chapter-army-unit-" + identifier + "-sid3-skill", asset)); }
				}
				skillSection.addElement(new ChangelogText(identifier + "-sid3", Style.NONE, textData.textStringForIdentifier(skillData.getMSID(skill)) + " (" + sid3 + ")"));
				section.addElement(skillSection);
			} else {
				section.addElement(new ChangelogText(identifier + "-sid3", Style.NONE, sid3));
			}
		}
		
		section.addElement(new ChangelogText(identifier + "-coordinates", Style.NONE, "(" + unit.getStartingX() + ", " + unit.getStartingY() + ") -> (" + unit.getEndingX() + ", " + unit.getEndingY() + ")"));
		
		return section;
	}
}
