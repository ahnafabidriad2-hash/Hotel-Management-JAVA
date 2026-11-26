import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

// sob window ekhan diye dekhano hoy, jate protita window er size, jayga, rong, font ek rokom hoy.
// protita page er nijer design thik thake, shudhu ekta standard window er majhkhane boshe
public class UI {
    public static final int WIDTH = 940;
    public static final int HEIGHT = 700;

    public static final Color BG = new Color(236, 242, 248);
    public static final Color HEADER = new Color(22, 52, 96);
    public static final Color PRIMARY = new Color(30, 90, 168);
    public static final Color DANGER = new Color(190, 40, 40);
    public static final Color TEXT = new Color(30, 30, 30);
    public static final String FONT = "Segoe UI";
    public static final String APP_NAME = "Hotel Management System";

    private static boolean ready = false;
    private static Image icon;

    // dialog (JOptionPane) er font o ek rokom korar jonno ekbar shuru te
    private static void init() {
        if (ready) return;
        ready = true;
        Font f = new Font(FONT, Font.PLAIN, 14);
        UIManager.put("OptionPane.messageFont", f);
        UIManager.put("OptionPane.buttonFont", new Font(FONT, Font.BOLD, 13));
        UIManager.put("OptionPane.background", BG);
        UIManager.put("Panel.background", BG);
        UIManager.put("ToolTip.font", f);

        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(PRIMARY);
        g.fillRoundRect(0, 0, 32, 32, 8, 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font(FONT, Font.BOLD, 22));
        g.drawString("H", 9, 24);
        g.dispose();
        icon = img;
    }

    // frame er setVisible(true) er bodole eita call hoy
    public static void show(JFrame f) {
        init();
        if (Boolean.TRUE.equals(f.getRootPane().getClientProperty("ui.done"))) {
            f.setVisible(true); // age theke sajano, shudhu abar dekhai
            return;
        }
        f.getRootPane().putClientProperty("ui.done", true);

        String page = f.getTitle();
        Dimension design = f.getSize();
        Container content = f.getContentPane();
        content.setPreferredSize(contentSize(content, design));
        style(content);

        // upore ekta header, nicher majhkhane page er asol design
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel app = new JLabel(APP_NAME);
        app.setFont(new Font(FONT, Font.BOLD, 20));
        app.setForeground(Color.WHITE);
        JLabel pageLabel = new JLabel(page);
        pageLabel.setFont(new Font(FONT, Font.PLAIN, 15));
        pageLabel.setForeground(new Color(200, 215, 235));
        header.add(app, BorderLayout.WEST);
        header.add(pageLabel, BorderLayout.EAST);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BG);
        center.add(content);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        f.setContentPane(root);

        f.setTitle(APP_NAME + " - " + page);
        f.setIconImage(icon);
        f.setSize(WIDTH, HEIGHT);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        closeBehaviour(f);
        f.setVisible(true);
    }

    public static void show(JDialog d) {
        init();
        style(d.getContentPane());
        d.setIconImage(icon);
        d.setResizable(false);
        d.setLocationRelativeTo(d.getOwner());
        d.setVisible(true);
    }

    // X chaple ei window bondho hoy; pichone ar kono window na thakle app bondho
    private static void closeBehaviour(JFrame f) {
        f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                f.dispose();
                for (Window w : Window.getWindows()) {
                    if (w != f && w.isVisible() && w instanceof JFrame) return;
                }
                System.exit(0);
            }
        });
    }

    // page ta asole koto boro: setSize er maap, ar null layout hole vitorer jinish jotodur jay
    private static Dimension contentSize(Container c, Dimension design) {
        int w = design.width - 16, h = design.height - 39;
        for (Component k : c.getComponents()) {
            Rectangle b = k.getBounds();
            if (c.getLayout() == null) {
                w = Math.max(w, b.x + b.width + 10);
                h = Math.max(h, b.y + b.height + 10);
            }
            // panel ke setBounds diye frame e boshano hole tar vitorer jinish o gona
            if (k instanceof Container ck && ck.getLayout() == null) {
                for (Component kk : ck.getComponents()) {
                    Rectangle bb = kk.getBounds();
                    w = Math.max(w, b.x + bb.x + bb.width + 10);
                    h = Math.max(h, b.y + bb.y + bb.height + 10);
                }
            }
        }
        return new Dimension(Math.min(w, WIDTH - 20), Math.min(h, HEIGHT - 100));
    }

    // vitorer sob jinish e ek rokom rong ar font
    public static void style(Component c) {
        if (c instanceof JButton b) {
            styleButton(b);
        } else if (c instanceof JLabel l) {
            Font f = l.getFont();
            l.setFont(new Font(FONT, f.getStyle(), Math.max(13, f.getSize())));
            l.setForeground(TEXT);
            // kichu label er nijer kalo/nil background chilo, lekha pora jeto na
            if (l.isOpaque()) l.setBackground(Color.WHITE);
        } else if (c instanceof JTextComponent t) {
            t.setFont(new Font(FONT, Font.PLAIN, 14));
            t.setBackground(Color.WHITE);
            t.setForeground(TEXT);
            t.setCaretColor(TEXT);
        } else if (c instanceof JComboBox<?> cb) {
            cb.setFont(new Font(FONT, Font.PLAIN, 14));
            cb.setBackground(Color.WHITE);
        } else if (c instanceof JRadioButton || c instanceof JCheckBox) {
            c.setFont(new Font(FONT, Font.PLAIN, 13));
            c.setBackground(BG);
            c.setForeground(TEXT);
        } else if (c instanceof JScrollPane sp) {
            sp.getViewport().setBackground(Color.WHITE);
        } else if (c instanceof JPanel || c instanceof JRootPane || c instanceof JLayeredPane) {
            c.setBackground(BG);
        }
        if (c instanceof Container ct) {
            for (Component k : ct.getComponents()) style(k);
        }
    }

    private static final java.util.Set<String> DANGER_WORDS = java.util.Set.of(
        "back", "logout", "log out", "exit", "cancel", "cancel booking", "back to homepage", "delete room",
        "delete customer", "delete employee");

    public static void styleButton(JButton b) {
        String t = b.getText() == null ? "" : b.getText().trim().toLowerCase();
        b.setBackground(DANGER_WORDS.contains(t) ? DANGER : PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(new Font(FONT, Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
