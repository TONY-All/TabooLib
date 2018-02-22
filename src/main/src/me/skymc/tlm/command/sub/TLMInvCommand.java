package me.skymc.tlm.command.sub;


import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.skymc.taboolib.commands.SubCommand;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.tlm.TLM;
import me.skymc.tlm.inventory.TLMInventoryHolder;
import me.skymc.tlm.module.TabooLibraryModule;
import me.skymc.tlm.module.sub.ModuleInventorySave;

/**
 * @author sky
 * @since 2018��2��18�� ����2:53:58
 */
public class TLMInvCommand extends SubCommand {

	/**
	 * @param sender
	 * @param args
	 */
	public TLMInvCommand(CommandSender sender, String[] args) {
		super(sender, args);
		if (TabooLibraryModule.getInst().valueOf("InventorySave") == null) {
			TLM.getInst().getLanguage().get("INV-DISABLE").send(sender);
			return;
		}
		
		// ��ȡģ��
		ModuleInventorySave moduleInventorySave = (ModuleInventorySave) TabooLibraryModule.getInst().valueOf("InventorySave");
		
		// �ж�����
		if (args.length == 1) {
			TLM.getInst().getLanguage().get("INV-EMPTY").send(sender);
		}
		
		// �г�����
		else if (args[1].equalsIgnoreCase("list")) {
			TLM.getInst().getLanguage().get("INV-LIST").addPlaceholder("$name", moduleInventorySave.getInventorys().toString()).send(sender);
		}
		
		// �鿴����
		else if (args[1].equalsIgnoreCase("info")) {
			// ����Ǻ�̨
			if (!(sender instanceof Player)) {
				TLM.getInst().getLanguage().get("INV-CONSOLE").send(sender);
				return;
			}
			
			// �жϳ���
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("INV-NAME").send(sender);
				return;
			}
			
			// �жϱ���
			if (!moduleInventorySave.getInventorys().contains(args[2])) {
				TLM.getInst().getLanguage().get("INV-NOTFOUND").addPlaceholder("$name", args[2]).send(sender);
				return;
			}
			
			// ��ȡ���
			Player player = (Player) sender;
			
			// ��ȡ��Ʒ
			List<ItemStack> items = moduleInventorySave.getItems(args[2]);
			
			// �򿪽���
			Inventory inv = Bukkit.createInventory(new TLMInventoryHolder("InventorySave"), 54, TLM.getInst().getLanguage().get("INV-INFO-TITLE")
					.addPlaceholder("$name", args[2])
					.asString());
			
			// ������Ʒ
			ItemStack barrier = ItemUtils.setName(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15), "��f"); 
			
			for (int i = 9 ; i < 18 ; i++) {
				inv.setItem(i, barrier);
			}
			
			for (int i = 9 ; i < 35 ; i++) {
				inv.setItem(i + 9, items.get(i));
			}
			
			for (int i = 0 ; i < 9 ; i++) {
				inv.setItem(i + 45, items.get(i));
			}
			
			inv.setItem(1, items.get(39));
			inv.setItem(2, items.get(38));
			inv.setItem(3, items.get(37));
			inv.setItem(4, items.get(36));
			
			// �жϰ汾
			if (items.size() == 41) {
				inv.setItem(6, items.get(40));
			}
			
			// �򿪱���
			player.openInventory(inv);
		}
		
		// ���汳��
		else if (args[1].equalsIgnoreCase("save")) {
			// ����Ǻ�̨
			if (!(sender instanceof Player)) {
				TLM.getInst().getLanguage().get("INV-CONSOLE").send(sender);
				return;
			}
			
			// �жϳ���
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("INV-NAME").send(sender);
				return;
			}
			
			// ��ȡ���
			Player player = (Player) sender;
			
			// ���汳��
			moduleInventorySave.saveInventory(player, args[2]);
			
			// ��ʾ��Ϣ
			TLM.getInst().getLanguage().get("INV-SAVE").addPlaceholder("$name", args[2]).send(player);
		}
		
		// ���Ǳ���
		else if (args[1].equalsIgnoreCase("paste")) {
			// �жϳ���
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("INV-NAME").send(sender);
				return;
			}
			
			// �жϱ���
			if (!moduleInventorySave.getInventorys().contains(args[2])) {
				TLM.getInst().getLanguage().get("INV-NOTFOUND").addPlaceholder("$name", args[2]).send(sender);
				return;
			}
			
			// ��ȡ���
			Player player;
			if (args.length > 3) {
				player = Bukkit.getPlayerExact(args[3]);
				// ��Ҳ�����
				if (player == null) {
					TLM.getInst().getLanguage().get("INV-OFFLINE").addPlaceholder("$name", args[3]).send(sender);
					return;
				}
			} else if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				TLM.getInst().getLanguage().get("INV-CONSOLE").send(sender);
				return;
			}
			
			// ���Ǳ���
			moduleInventorySave.pasteInventory(player, args[2]);
			
			// ��������
			if (sender instanceof Player) {
				// ��ʾ��Ϣ
				TLM.getInst().getLanguage().get("INV-PASTE")
					.addPlaceholder("$name", args[2])
					.addPlaceholder("$player", player.getName())
					.send(player);
			}
		}
		
		// ɾ������
		else if (args[1].equalsIgnoreCase("delete")) {
			// �жϳ���
			if (args.length < 3) {
				TLM.getInst().getLanguage().get("INV-NAME").send(sender);
				return;
			}
			
			// �жϱ���
			if (!moduleInventorySave.getInventorys().contains(args[2])) {
				TLM.getInst().getLanguage().get("INV-NOTFOUND").addPlaceholder("$name", args[2]).send(sender);
				return;
			}
			
			// ɾ��
			moduleInventorySave.deleteInventory(args[2]);
			
			// ��ʾ��Ϣ
			TLM.getInst().getLanguage().get("KIT-DELETE").addPlaceholder("$name", args[2]).send(sender);
		}
		
		else {
			TLM.getInst().getLanguage().get("INV-EMPTY").send(sender);
		}
	}
}