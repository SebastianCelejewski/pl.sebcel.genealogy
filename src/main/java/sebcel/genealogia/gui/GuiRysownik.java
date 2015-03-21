package sebcel.genealogia.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import sebcel.genealogia.lib.DatabaseDelegate;
import sebcel.genealogia.struct.DiagramInfoStruct;
import sebcel.genealogia.struct.DrzewoOsobaStruct;
import sebcel.genealogia.struct.DrzewoRodzinaStruct;

import com.sun.jimi.core.Jimi;

public class GuiRysownik extends JFrame implements ActionListener, IDrawOptionsListener {

    public final static long serialVersionUID = 0l;

    private static Color kolorOsoby = Color.RED;
    private static Color kolorMalzonka = Color.BLUE;
    private static Color kolorInfo = Color.GRAY;
    private static Color kolorDzieci = Color.BLACK;
    private static Color kolorMalzonkow = new Color(200, 200, 200);
    private static Color kolorSlubu = new Color(0, 200, 0);

    private final JPanel panelKlawiszy = new JPanel();
    private final PanelOpcji panelOpcji = new PanelOpcji();
    private final JButton klawiszZapisz = new JButton("Zapisz");
    private final JButton klawiszZamknij = new JButton("Zamknij");
    private final JButton klawiszOpcje = new JButton("Opcje");
    private final Drzewo drzewo = new Drzewo();
    private final JScrollPane scrollPane = new JScrollPane(new JLabel("Proszê czekaæ...", JLabel.CENTER));

    private DiagramInfoStruct diagramInfo;

    public GuiRysownik() {
        panelKlawiszy.add(klawiszZapisz);
        panelKlawiszy.add(klawiszZamknij);
        panelKlawiszy.add(klawiszOpcje);

        klawiszZapisz.addActionListener(this);
        klawiszZamknij.addActionListener(this);
        klawiszOpcje.addActionListener(this);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(panelKlawiszy, BorderLayout.SOUTH);
        panelOpcji.setDrawOptionsListener(this);
    }

    public void rysuj(final DiagramInfoStruct diagramInfo) {
        this.setSize(800, 600);
        this.setLocation(100, 100);
        this.setTitle(diagramInfo.opis);
        this.setVisible(true);
        this.diagramInfo = diagramInfo;

        new Thread() {
            public void run() {
                rysuj(new OpcjeRysowania());
            }
        }.start();
    }

    @Override
    public void updateDrawing(OpcjeRysowania opcjeRysowania) {
        rysuj(opcjeRysowania);
    }

    private void rysuj(OpcjeRysowania opcjeRysowania) {
        System.out.println("rysujê");
        Image obrazDrzewa = rysujDrzewo(diagramInfo.idKorzenia, new Font("Courier", Font.PLAIN, 12), 20, opcjeRysowania);
        drzewo.setImage(obrazDrzewa);
        scrollPane.setViewportView(GuiRysownik.this.drzewo);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(klawiszZamknij)) {
            this.setVisible(false);
        }

        if (e.getSource().equals(klawiszOpcje)) {
            panelOpcji.setVisible(true);
        }

        if (e.getSource().equals(klawiszZapisz)) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    String end = f.getName().substring(f.getName().length() - 4).toLowerCase();
                    if (end.equals(".png"))
                        return true;
                    if (end.equals(".jpg"))
                        return true;
                    if (end.equals(".gif"))
                        return true;
                    if (end.equals(".bmp"))
                        return true;
                    if (end.equals(".png"))
                        return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return "Supported graphics formats";
                }

            });

            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    String filename = fileChooser.getSelectedFile().getCanonicalPath();
                    Image image = drzewo.getImage();
                    Jimi.putImage(image, filename);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Wyst¹pi³ b³¹d:\n" + ex.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Image rysujDrzewo(Long idOsoby, Font font, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {
        Dimension bufferDimensions = new Dimension(10 * font.getSize() * szerokoscPokolenia, 500 * font.getSize());
        BufferedImage image = new BufferedImage(bufferDimensions.width, bufferDimensions.height, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bufferDimensions.width, bufferDimensions.height);
        g.setFont(font);
        Dimension wymiary = rysuj(idOsoby, g, 0, 0, szerokoscPokolenia, opcjeRysowania);
        BufferedImage newImage = new BufferedImage(wymiary.width, wymiary.height, BufferedImage.TYPE_BYTE_INDEXED);
        newImage.getGraphics().drawImage(image, 0, 0, new ImageObserver() {

            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return true;
            }

        });
        return newImage;
    }

    private Dimension rysuj(Long idOsoby, Graphics g, int x, int y, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {
        int fontSize = g.getFont().getSize();
        int szerokosc = szerokoscPokolenia * fontSize;
        int wysokosc = fontSize;

        DrzewoOsobaStruct osoba = DatabaseDelegate.getDaneOsoby(idOsoby);
        String nazwa = osoba.nazwa;
        if (opcjeRysowania.isPokazId()) {
            nazwa += " (" + osoba.id + ")";
        }

        g.setColor(kolorOsoby);
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, fontSize));
        g.drawString(nazwa, x, y + wysokosc);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));

        g.setColor(kolorInfo);
        String urInfo = osoba.daneUrodzenia;
        if (urInfo.length() > 0 && opcjeRysowania.isPokazDaneUrodzenia()) {
            wysokosc += fontSize;
            g.drawString(urInfo, x, y + wysokosc);
        }
        String smInfo = osoba.daneSmierci;
        if (smInfo.length() > 0 && opcjeRysowania.isPokazDaneSmierci()) {
            wysokosc += fontSize;
            g.drawString(smInfo, x, y + wysokosc);
        }
        String zamInfo = osoba.daneZamieszkania;
        if (zamInfo.length() > 0 && opcjeRysowania.isPokazDaneZamieszkania()) {
            wysokosc += fontSize;
            g.drawString(zamInfo, x, y + wysokosc);
        }
        String zawInfo = osoba.daneZawodu;
        if (zawInfo.length() > 0 && opcjeRysowania.isPokazWyksztalcenie()) {
            wysokosc += fontSize;
            g.drawString(zawInfo, x, y + wysokosc);
        }
        if (urInfo.length() > 0 || smInfo.length() > 0 || zawInfo.length() > 0 || zamInfo.length() > 0) {
            wysokosc += fontSize / 2;
        }

        int szer = (int) g.getFont().getStringBounds(nazwa, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
        Dimension wymiaryRodzin = rysujRodziny(osoba, g, x, y + wysokosc, szer, wysokosc, szerokoscPokolenia, opcjeRysowania);
        wysokosc += wymiaryRodzin.height;
        if (wymiaryRodzin.width > szerokosc) {
            szerokosc = wymiaryRodzin.width;
        }

        return new Dimension(szerokosc, wysokosc);
    }

    private Dimension rysujRodziny(DrzewoOsobaStruct osoba, Graphics g, int x, int y, int szerMalzonka, int wysMalzonka, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {
        int fontSize = g.getFont().getSize();
        int wysokosc = 0;
        int szerokosc = szerokoscPokolenia * fontSize;

        List<DrzewoRodzinaStruct> zwiazki = osoba.rodziny;

        int wysokoscRodzin = 0;
        if (zwiazki != null && zwiazki.size() > 0) {
            int x1 = 0;
            int y1 = 0;
            int licznik = 0;
            for (DrzewoRodzinaStruct zwiazek : zwiazki) {
                Long idMalzonka = zwiazek.idMalzonka;
                DrzewoOsobaStruct malzonek = DatabaseDelegate.getDaneOsoby(idMalzonka);

                int x0 = x;
                int y0 = y + wysokosc;
                if (x1 > 0 && y1 > 0) {
                    g.setColor(kolorMalzonkow);
                    g.drawLine(x0, y0, x1, y1);
                }

                x1 = x0;
                y1 = y0 + fontSize;
                Dimension wymiaryRodziny = rysujRodzine(zwiazek, malzonek, g, x, y + wysokosc, licznik == 0, szerMalzonka, wysMalzonka, szerokoscPokolenia, opcjeRysowania);
                int wysokoscRodziny = wymiaryRodziny.height;
                wysokoscRodzin += wysokoscRodziny;
                if (wymiaryRodziny.width > szerokosc) {
                    szerokosc = wymiaryRodziny.width;
                }
                wysokosc += wysokoscRodziny + fontSize;
                licznik++;
            }
        }

        return new Dimension(szerokosc, wysokosc);
    }

    private Dimension rysujRodzine(DrzewoRodzinaStruct zwiazek, DrzewoOsobaStruct malzonek, Graphics g, int x, int y, boolean obnizenie, int szerMalzonka, int wysMalzonka, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {

        int fontSize = g.getFont().getSize();
        int rozmiar = fontSize * szerokoscPokolenia;

        int wysokosc = fontSize;
        int szerokosc = szerokoscPokolenia * fontSize;

        int szerokoscDzieci = 0;
        int wysokoscDzieci = 0;

        Dimension wymiaryMalzonka = rysujMalzonka(zwiazek, malzonek, g, x, y, szerokoscPokolenia, opcjeRysowania);
        wysokosc += wymiaryMalzonka.height;

        String nazwaMalzonka = malzonek.nazwa;
        if (opcjeRysowania.isPokazId()) {
            nazwaMalzonka += " (" + malzonek.id + ")";
        }

        int szer = (int) g.getFont().getStringBounds("+ " + nazwaMalzonka, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
        int mar = 5;

        if (zwiazek.idDzieci != null && zwiazek.idDzieci.size() > 0) {
            int yy = y;
            int xx = x + szer + mar;
            if (obnizenie) {
                yy -= wysMalzonka;
                xx = x + szerMalzonka + mar;
            }
            Dimension wymiaryDzieci = rysujDzieci(zwiazek.idDzieci, g, x + rozmiar, yy, szerokoscPokolenia, opcjeRysowania);
            wysokoscDzieci += wymiaryDzieci.height;

            if (wymiaryDzieci.width > szerokoscDzieci) {
                szerokoscDzieci = wymiaryDzieci.width;
            }
            g.setColor(kolorDzieci);
            g.drawLine(xx, yy + fontSize / 2, x + rozmiar - mar, yy + fontSize / 2);
        }

        if (obnizenie) {
            wysokoscDzieci -= wysMalzonka;
        }
        int wysokoscRodziny = Math.max(wymiaryMalzonka.height, wysokoscDzieci);

        return new Dimension(szerokosc + szerokoscDzieci, wysokoscRodziny);
    }

    private Dimension rysujMalzonka(DrzewoRodzinaStruct zwiazek, DrzewoOsobaStruct malzonek, Graphics g, int x, int y, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {
        int fontSize = g.getFont().getSize();
        int wysokosc = fontSize;

        String nazwa = malzonek.nazwa;
        if (opcjeRysowania.isPokazId()) {
            nazwa += " (" + malzonek.id + ")";
        }

        g.setColor(kolorMalzonka);
        int rozmiar = fontSize * szerokoscPokolenia;
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, fontSize));
        g.drawString("+ " + nazwa, x, y + fontSize);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));

        g.setColor(kolorInfo);
        String urInfo = malzonek.daneUrodzenia;
        if (urInfo.length() > 0 && opcjeRysowania.isPokazDaneUrodzenia()) {
            g.setColor(kolorInfo);
            wysokosc += fontSize;
            g.drawString(urInfo, x, y + wysokosc);
        }
        String smInfo = malzonek.daneSmierci;
        if (smInfo.length() > 0 && opcjeRysowania.isPokazDaneSmierci()) {
            g.setColor(kolorInfo);
            wysokosc += fontSize;
            g.drawString(smInfo, x, y + wysokosc);
        }
        String zamInfo = malzonek.daneZamieszkania;
        if (zamInfo.length() > 0 && opcjeRysowania.isPokazDaneZamieszkania()) {
            wysokosc += fontSize;
            g.drawString(zamInfo, x, y + wysokosc);
        }
        String zawInfo = malzonek.daneZawodu;
        if (zawInfo.length() > 0 && opcjeRysowania.isPokazWyksztalcenie()) {
            wysokosc += fontSize;
            g.drawString(zawInfo, x, y + wysokosc);
        }

        wysokosc += fontSize / 2;

        if (opcjeRysowania.isPokazId()) {
            g.setColor(kolorSlubu);
            String zwiazekInfo = "Id zwi¹zku: " + zwiazek.idZwiazku;
            wysokosc += fontSize;
            g.drawString(zwiazekInfo, x, y + wysokosc);
        }

        String pozInfo = zwiazek.daneSpotkania;
        if (pozInfo.length() > 0 && opcjeRysowania.isPokazDanePoznaniaSie()) {
            g.setColor(kolorSlubu);
            wysokosc += fontSize;
            g.drawString(pozInfo, x, y + wysokosc);
        }
        String slInfo = zwiazek.daneSlubu;
        if (slInfo.length() > 0 && opcjeRysowania.isPokazDaneSlubu()) {
            g.setColor(kolorSlubu);
            wysokosc += fontSize;
            g.drawString(slInfo, x, y + wysokosc);
        }
        String rozInfo = zwiazek.daneRozstania;
        if (rozInfo.length() > 0 && opcjeRysowania.isPokazDaneRozstaniaSie()) {
            g.setColor(kolorSlubu);
            wysokosc += fontSize;
            g.drawString(rozInfo, x, y + wysokosc);
        }
        String rzwInfo = zwiazek.daneRozwodu;
        if (rzwInfo.length() > 0 && opcjeRysowania.isPokazDaneRozwodu()) {
            g.setColor(kolorSlubu);
            wysokosc += fontSize;
            g.drawString(rzwInfo, x, y + wysokosc);
        }

        return new Dimension(rozmiar, wysokosc);
    }

    private Dimension rysujDzieci(List<Long> idDzieci, Graphics g, int x, int y, int szerokoscPokolenia, OpcjeRysowania opcjeRysowania) {
        int fontSize = g.getFont().getSize();
        int wysokosc = 0;
        int szerokosc = szerokoscPokolenia * fontSize;
        int staryX0 = 0;
        int staryY0 = 0;

        if (idDzieci != null && idDzieci.size() > 0) {
            for (Long idDziecka : idDzieci) {
                Dimension wymiaryDziecka = rysuj(idDziecka, g, x + fontSize, y + wysokosc, szerokoscPokolenia, opcjeRysowania);
                int wysokoscDziecka = wymiaryDziecka.height;
                if (wymiaryDziecka.width > szerokosc) {
                    szerokosc = wymiaryDziecka.width;
                }
                int x0 = x - fontSize / 2;
                int y0 = y + wysokosc + fontSize / 2;
                int x1 = x + fontSize / 2;
                int y1 = y + wysokosc + fontSize / 2;
                g.setColor(kolorDzieci);
                g.drawLine(x0, y0, x1, y1);
                if (staryX0 > 0 && staryY0 > 0) {
                    g.drawLine(x0, y0, staryX0, staryY0);
                }
                wysokosc += wysokoscDziecka + fontSize;
                staryX0 = x0;
                staryY0 = y0;
            }
        }

        return new Dimension(szerokosc, wysokosc);
    }
}