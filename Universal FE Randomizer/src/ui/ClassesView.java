package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

public class ClassesView extends Composite {
	
	private Group container;
	
	private Button randomizePCButton;
	private Button randomizePCLordsButton;
	private Button randomizePCThievesButton;

	private Button randomizeEnemiesButton;
	
	private Button randomizeBossesButton;

	public ClassesView(Composite parent, int style) {
		super(parent, style);
		
		FillLayout layout = new FillLayout();
		setLayout(layout);
		
		container = new Group(this, SWT.NONE);
		
		container.setText("Classes");
		container.setToolTipText("Randomize classes for all characters.");
		
		FormLayout mainLayout = new FormLayout();
		mainLayout.marginLeft = 5;
		mainLayout.marginTop = 5;
		mainLayout.marginBottom = 5;
		mainLayout.marginRight = 5;
		container.setLayout(mainLayout);
		
		randomizePCButton = new Button(container, SWT.CHECK);
		randomizePCButton.setText("Randomize Playable Characters");
		randomizePCButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				randomizePCLordsButton.setEnabled(randomizePCButton.getSelection());
				randomizePCThievesButton.setEnabled(randomizePCButton.getSelection());
			}
		});
		
		FormData pcFormData = new FormData();
		pcFormData.left = new FormAttachment(0, 5);
		pcFormData.top = new FormAttachment(0, 5);
		randomizePCButton.setLayoutData(pcFormData);
		
		randomizePCLordsButton = new Button(container, SWT.CHECK);
		randomizePCLordsButton.setText("Include Lords");
		randomizePCLordsButton.setToolTipText("If enabled, allows lords to be changed to random classes, as well as adds lords to the randomizable class pool.");
		randomizePCLordsButton.setEnabled(false);
		
		FormData pcLordsFormData = new FormData();
		pcLordsFormData.left = new FormAttachment(randomizePCButton, 10, SWT.LEFT);
		pcLordsFormData.top = new FormAttachment(randomizePCButton, 5);
		randomizePCLordsButton.setLayoutData(pcLordsFormData);
		
		randomizePCThievesButton = new Button(container, SWT.CHECK);
		randomizePCThievesButton.setText("Include Thieves");
		randomizePCThievesButton.setToolTipText("If enabled, allows thieves to be changed to random classes, as well as adds thieves to the randomizable class pool.");
		randomizePCThievesButton.setEnabled(false);
		
		FormData pcThievesFormData = new FormData();
		pcThievesFormData.left = new FormAttachment(randomizePCLordsButton, 0, SWT.LEFT);
		pcThievesFormData.top = new FormAttachment(randomizePCLordsButton, 5);
		randomizePCThievesButton.setLayoutData(pcThievesFormData);
		
		//////////////////////////////////////////////////////////////////
		
		randomizeEnemiesButton = new Button(container, SWT.CHECK);
		randomizeEnemiesButton.setText("Randomize Regular Enemies");
		
		FormData enemyFormData = new FormData();
		enemyFormData.left = new FormAttachment(randomizePCButton, 0, SWT.LEFT);
		enemyFormData.top = new FormAttachment(randomizePCThievesButton, 10);
		randomizeEnemiesButton.setLayoutData(enemyFormData);
		
		//////////////////////////////////////////////////////////////////
		
		randomizeBossesButton = new Button(container, SWT.CHECK);
		randomizeBossesButton.setText("Randomize Bosses");
		
		FormData bossFormData = new FormData();
		bossFormData.left = new FormAttachment(randomizeEnemiesButton, 0, SWT.LEFT);
		bossFormData.top = new FormAttachment(randomizeEnemiesButton, 10);
		randomizeBossesButton.setLayoutData(bossFormData);
	}

	public Boolean shouldRandomizePlayableCharacters() {
		return randomizePCButton.getSelection();
	}
	
	public Boolean shouldRandomizeLords() {
		return randomizePCLordsButton.getEnabled() && randomizePCLordsButton.getSelection();
	}
	
	public Boolean shouldRandomizeThieves() {
		return randomizePCThievesButton.getEnabled() && randomizePCThievesButton.getSelection();
	}
	
	public Boolean shouldRandomizeEnemies() {
		return randomizeEnemiesButton.getSelection();
	}
	
	public Boolean shouldRandomizeBosses() {
		return randomizeBossesButton.getSelection();
	}
}