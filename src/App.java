import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HanoiFrame frame = new HanoiFrame();
            frame.setVisible(true);
        });
    }
}

class HanoiFrame extends JFrame {

    private static final int MIN_DISCOS = 3;
    private static final int MAX_DISCOS = 8;

    private final HanoiPanel hanoiPanel;
    private final JLabel estadoLabel;
    private final JSpinner discosSpinner;
    private final JButton resolverBtn;
    private final JButton reiniciarBtn;

    private Timer animacionTimer;
    private List<Movimiento> movimientos;
    private int indiceMovimiento;
    private int totalMovimientos;

    HanoiFrame() {
        setTitle("Torres de Hanoi V.2 - Interfaz Grafica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(20, 27, 39));
        topPanel.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JLabel titulo = new JLabel("TORRES DE HANOI V.2");
        titulo.setForeground(new Color(238, 245, 255));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel discosLabel = new JLabel("Discos:");
        discosLabel.setForeground(new Color(200, 216, 240));
        discosLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        discosSpinner = new JSpinner(new SpinnerNumberModel(4, MIN_DISCOS, MAX_DISCOS, 1));
        discosSpinner.setPreferredSize(new Dimension(70, 28));

        resolverBtn = new JButton("Resolver");
        resolverBtn.setFocusPainted(false);
        resolverBtn.setBackground(new Color(27, 153, 139));
        resolverBtn.setForeground(Color.WHITE);

        reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.setFocusPainted(false);
        reiniciarBtn.setBackground(new Color(230, 126, 34));
        reiniciarBtn.setForeground(Color.WHITE);

        estadoLabel = new JLabel("Listo para iniciar");
        estadoLabel.setForeground(new Color(200, 216, 240));
        estadoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        topPanel.add(titulo);
        topPanel.add(new JLabel("      "));
        topPanel.add(discosLabel);
        topPanel.add(discosSpinner);
        topPanel.add(new JLabel("   "));
        topPanel.add(resolverBtn);
        topPanel.add(reiniciarBtn);
        topPanel.add(new JLabel("      "));
        topPanel.add(estadoLabel);

        hanoiPanel = new HanoiPanel((int) discosSpinner.getValue());
        add(topPanel, BorderLayout.NORTH);
        add(hanoiPanel, BorderLayout.CENTER);

        resolverBtn.addActionListener(e -> iniciarResolucion());
        reiniciarBtn.addActionListener(e -> reiniciarJuego());
    }

    private void reiniciarJuego() {
        detenerAnimacion();
        int discos = (int) discosSpinner.getValue();
        hanoiPanel.reiniciar(discos);
        estadoLabel.setText("Reiniciado con " + discos + " discos");
        resolverBtn.setEnabled(true);
        discosSpinner.setEnabled(true);
    }

    private void iniciarResolucion() {
        detenerAnimacion();
        int discos = (int) discosSpinner.getValue();

        hanoiPanel.reiniciar(discos);
        movimientos = new ArrayList<>();
        generarMovimientos(discos, 0, 2, 1, movimientos);
        indiceMovimiento = 0;
        totalMovimientos = movimientos.size();

        resolverBtn.setEnabled(false);
        discosSpinner.setEnabled(false);
        estadoLabel.setText("Resolviendo... 0/" + totalMovimientos + " movimientos");

        animacionTimer = new Timer(500, e -> {
            if (indiceMovimiento >= totalMovimientos) {
                detenerAnimacion();
                estadoLabel.setText("Completado en " + totalMovimientos + " movimientos");
                resolverBtn.setEnabled(true);
                discosSpinner.setEnabled(true);
                JOptionPane.showMessageDialog(this,
                        "Solucion completada con " + totalMovimientos + " movimientos.",
                        "Torres de Hanoi",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Movimiento m = movimientos.get(indiceMovimiento);
            hanoiPanel.moverDisco(m.origen, m.destino);
            indiceMovimiento++;
            estadoLabel.setText("Resolviendo... " + indiceMovimiento + "/" + totalMovimientos + " movimientos");
        });
        animacionTimer.start();
    }

    private void detenerAnimacion() {
        if (animacionTimer != null && animacionTimer.isRunning()) {
            animacionTimer.stop();
        }
    }

    private void generarMovimientos(int n, int origen, int destino, int auxiliar, List<Movimiento> lista) {
        if (n == 1) {
            lista.add(new Movimiento(origen, destino));
            return;
        }
        generarMovimientos(n - 1, origen, auxiliar, destino, lista);
        lista.add(new Movimiento(origen, destino));
        generarMovimientos(n - 1, auxiliar, destino, origen, lista);
    }

    private static class Movimiento {
        final int origen;
        final int destino;

        Movimiento(int origen, int destino) {
            this.origen = origen;
            this.destino = destino;
        }
    }
}

class HanoiPanel extends JPanel {

    private final Stack<Integer>[] torres;
    private int numeroDiscos;

    @SuppressWarnings("unchecked")
    HanoiPanel(int discos) {
        setBackground(new Color(244, 248, 255));
        torres = new Stack[3];
        for (int i = 0; i < 3; i++) {
            torres[i] = new Stack<>();
        }
        reiniciar(discos);
    }

    final void reiniciar(int discos) {
        numeroDiscos = discos;
        for (Stack<Integer> torre : torres) {
            torre.clear();
        }
        for (int i = numeroDiscos; i >= 1; i--) {
            torres[0].push(i);
        }
        repaint();
    }

    void moverDisco(int origen, int destino) {
        if (!torres[origen].isEmpty()) {
            int disco = torres[origen].pop();
            torres[destino].push(disco);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint fondo = new GradientPaint(0, 0, new Color(227, 236, 250), 0, h, new Color(247, 251, 255));
        g2.setPaint(fondo);
        g2.fillRect(0, 0, w, h);

        int baseY = h - 90;
        int torreAlto = 280;
        int torreAncho = 14;
        int baseAncho = w - 140;
        int baseX = 70;

        g2.setColor(new Color(69, 77, 102));
        g2.fillRoundRect(baseX, baseY, baseAncho, 18, 18, 18);

        int[] centrosX = { w / 6, w / 2, (w * 5) / 6 };

        g2.setColor(new Color(57, 65, 90));
        for (int cx : centrosX) {
            g2.fillRoundRect(cx - torreAncho / 2, baseY - torreAlto, torreAncho, torreAlto, 12, 12);
        }

        int discoAltura = Math.max(20, Math.min(34, 240 / Math.max(1, numeroDiscos)));
        int minAncho = 52;
        int maxAncho = 176;
        int paso = (maxAncho - minAncho) / Math.max(1, numeroDiscos - 1);

        for (int i = 0; i < 3; i++) {
            Stack<Integer> torre = torres[i];
            for (int j = 0; j < torre.size(); j++) {
                int tamDisco = torre.get(j);
                int anchoDisco = minAncho + (tamDisco - 1) * paso;
                int x = centrosX[i] - (anchoDisco / 2);
                int y = baseY - ((j + 1) * discoAltura);

                g2.setColor(colorDisco(tamDisco));
                g2.fillRoundRect(x, y, anchoDisco, discoAltura - 2, 16, 16);

                g2.setColor(new Color(25, 30, 42, 170));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(x, y, anchoDisco, discoAltura - 2, 16, 16);
            }
        }

        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2.setColor(new Color(43, 50, 72));
        g2.drawString("A", centrosX[0] - 5, baseY + 38);
        g2.drawString("B", centrosX[1] - 5, baseY + 38);
        g2.drawString("C", centrosX[2] - 5, baseY + 38);
    }

    private Color colorDisco(int tamano) {
        Color[] paleta = {
                new Color(239, 71, 111),
                new Color(255, 159, 67),
                new Color(255, 209, 102),
                new Color(6, 214, 160),
                new Color(17, 138, 178),
                new Color(7, 59, 76),
                new Color(131, 56, 236),
                new Color(58, 134, 255)
        };
        return paleta[(tamano - 1) % paleta.length];
    }
}
