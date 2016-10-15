package es.uvigo.ei.sing.hlfernandez.ui.icons;

import javax.swing.ImageIcon;

public class Icons {
	public static final ImageIcon ICON_HELP = getResource("icons/help.png");
	
	public static final ImageIcon ICON_ADD_32 = getResource("icons/add32.png");
	public static final ImageIcon ICON_ADD_24 = getResource("icons/add24.png");
	public static final ImageIcon ICON_ADD_16 = getResource("icons/add16.png");

	public static final ImageIcon ICON_TRASH_32 = getResource("icons/trash32.png");
	public static final ImageIcon ICON_TRASH_24 = getResource("icons/trash24.png");
	public static final ImageIcon ICON_TRASH_16 = getResource("icons/trash16.png");
	
	public static final ImageIcon ICON_EYE_32 = getResource("icons/eye32.png");
	public static final ImageIcon ICON_EYE_24 = getResource("icons/eye24.png");
	public static final ImageIcon ICON_EYE_16 = getResource("icons/eye16.png");
	
	public static final ImageIcon ICON_EYE_HIDDEN_32 = getResource("icons/eye-hidden32.png");
	public static final ImageIcon ICON_EYE_HIDDEN_24 = getResource("icons/eye-hidden24.png");
	public static final ImageIcon ICON_EYE_HIDDEN_16 = getResource("icons/eye-hidden16.png");
	
	public static final ImageIcon ICON_EDIT_32 = getResource("icons/edit32.png");
	public static final ImageIcon ICON_EDIT_24 = getResource("icons/edit24.png");
	public static final ImageIcon ICON_EDIT_16 = getResource("icons/edit16.png");

	public static final ImageIcon ICON_EXPORT_32 = getResource("icons/export32.png");
	public static final ImageIcon ICON_EXPORT_24 = getResource("icons/export24.png");
	public static final ImageIcon ICON_EXPORT_16 = getResource("icons/export16.png");
	
	public static final ImageIcon ICON_INFO_32 = getResource("icons/info32.png");
	public static final ImageIcon ICON_INFO_24 = getResource("icons/info24.png");
	public static final ImageIcon ICON_INFO_16 = getResource("icons/info16.png");
	
	public static final ImageIcon ICON_LOOKUP_32 = getResource("icons/lookup32.png");
	public static final ImageIcon ICON_LOOKUP_24 = getResource("icons/lookup24.png");
	public static final ImageIcon ICON_LOOKUP_16 = getResource("icons/lookup16.png");

	public static final ImageIcon ICON_TABLE_32 = getResource("icons/table32.png");
	public static final ImageIcon ICON_TABLE_24 = getResource("icons/table24.png");
	public static final ImageIcon ICON_TABLE_16 = getResource("icons/table16.png");
	
	public static ImageIcon getResource(String resource) {
		return new ImageIcon(Icons.class.getResource(resource));
	}
}
