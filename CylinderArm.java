/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cylinderarm;
import com.sun.j3d.utils.applet.MainFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;
import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
//#ifdef __HAS_NIO__
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
//#endif
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.geometry.Box;
import javax.vecmath.Point2f;
/**
 *
 * @author Asus
 */public class CylinderArm extends JFrame implements KeyListener{
    
    private TransformGroup obrot_animacja = new TransformGroup();
    private TransformGroup obrot_animacja1 = new TransformGroup();
    private TransformGroup obrot_animacja2 = new TransformGroup();
    private TransformGroup obrot_animacja3 = new TransformGroup();
    private TransformGroup obrot_animacja4 = new TransformGroup();
    private Matrix4d matrix = new Matrix4d();
    private float x1 =0.2f;
    private float    y1=0.0f ;
    private JButton right,left,top,up;
    private JTextArea oknoTekstowe;
    
    private float kat=0.0f;
    private float kx=0.0f;
    private Matrix4d macierz = new Matrix4d();
    private Matrix4d macierz2 = new Matrix4d();
    
    private Transform3D  p_chwytak   = new Transform3D();
    private Transform3D  p_chwytak1   = new Transform3D();
    private Transform3D  p_chwytak2   = new Transform3D();
    private Transform3D  p_naped   = new Transform3D();
    private Transform3D  p_podstawa   = new Transform3D();
    private Transform3D  tmp_rot = new Transform3D();
    
        BranchGroup utworzScene(){
        BranchGroup wezel_scena = new BranchGroup();
        
        
        int i ;
        
        Appearance  wygladChwytak = new Appearance();
        wygladChwytak.setColoringAttributes(new ColoringAttributes(0.56f, 0.57f, 0.6f,ColoringAttributes.NICEST));
        Box chwytak = new Box(0.3f, 0.03f,0.03f,Box.GENERATE_TEXTURE_COORDS|Box.GENERATE_NORMALS , wygladChwytak);
        obrot_animacja = new TransformGroup();
        obrot_animacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        p_chwytak.set(new Vector3d(0.2f,0.0f,0.0f));  
        obrot_animacja.setTransform(p_chwytak);
        obrot_animacja.addChild(chwytak);
        wezel_scena.addChild(obrot_animacja); 
        
        // palce chwytaka czesc pierwsza (gorna)
    Box chwytak1 = new Box(0.05f, 0.025f, 0.04f,Cylinder.GENERATE_NORMALS| Cylinder.GENERATE_TEXTURE_COORDS, wygladChwytak);  
    obrot_animacja1 = new TransformGroup();
    obrot_animacja1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    p_chwytak1.set(new Vector3f(0.5f,0.05f,0.0f));
    obrot_animacja1.setTransform(p_chwytak1);
    obrot_animacja1.addChild(chwytak1);
    wezel_scena.addChild(obrot_animacja1);  
     
    // palce chwytaka czesc druga(dolna)
    Box chwytak2 = new Box(0.05f, 0.025f, 0.04f,Cylinder.GENERATE_NORMALS| Cylinder.GENERATE_TEXTURE_COORDS, wygladChwytak);
    obrot_animacja4 = new TransformGroup();
    obrot_animacja4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    p_chwytak2.set(new Vector3f(0.5f,-0.05f,0.0f));
    obrot_animacja4.setTransform(p_chwytak2);
    obrot_animacja4.addChild(chwytak2);
    wezel_scena.addChild(obrot_animacja4);
   
        Appearance  wygladnaped = new Appearance();
        Texture metal = new TextureLoader("obrazki/robot.jpg",this).getTexture();
        wygladnaped.setTexture(metal);
        Box naped = new Box(0.3f, 0.1f,0.1f,Cylinder.GENERATE_TEXTURE_COORDS|Box.GENERATE_NORMALS,wygladnaped);
        p_naped.set(new Vector3d(0.1f,0.0f,0.0f));  
        obrot_animacja2.setTransform(p_naped);
        obrot_animacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        obrot_animacja2.addChild(naped);
        wezel_scena.addChild(obrot_animacja2);
        
        p_podstawa.set(new Vector3f(0.0f,0.0f,0.0f));
        obrot_animacja3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        wezel_scena.addChild(obrot_animacja3); 
        Appearance  wygladPodstawa = new Appearance();
        Material material = new Material(new Color3f(0.5f,0.3f,0.2f),new Color3f(0.1f,0.1f,0.1f), new Color3f(0.8f,0.1f,0.5f), new Color3f(0.2f,0.2f,0.2f), 20.0f);
        wygladPodstawa.setMaterial(material);
        Cylinder podstawa = new Cylinder(0.1f,0.9f, Cylinder.GENERATE_NORMALS,wygladPodstawa);
        p_podstawa.set(new Vector3f(0.0f,0.0f,0.0f));
        TransformGroup transformacja_podstawa = new TransformGroup(p_podstawa);
        transformacja_podstawa.addChild(podstawa);
        obrot_animacja3.addChild(transformacja_podstawa);
         
        Appearance wyglad_niebo = new Appearance();
        Texture niebo = new TextureLoader("obrazki/kosmos.jpg",this).getTexture();
        wyglad_niebo.setTexture(niebo);
        Sphere kula = new Sphere (3.5f,Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS,80, wyglad_niebo ); 
        wezel_scena.addChild(kula); 
          
            
         //TEKSTURY==============================
        BoundingSphere Wiezy = new BoundingSphere();
        AmbientLight Swiatlo = new AmbientLight();
        Swiatlo.setInfluencingBounds(Wiezy);
        wezel_scena.addChild(Swiatlo);
        //==============================
        SpotLight SwiatloStozkowe = new SpotLight(new Color3f(1.0f,1.0f,1.0f),new Point3f(-1.5f,1.5f,1.5f), new Point3f(-0.01f,0.04f,0.01f),
                                                  new Vector3f(1.0f,1.0f,1.0f), (float)Math.PI,200);
        SwiatloStozkowe.setInfluencingBounds(Wiezy);
        wezel_scena.addChild(SwiatloStozkowe);
        //==============================
        Texture TeksturaMur = new TextureLoader("obrazki/trawka.jpg", this).getTexture();
        Appearance WygladMur = new Appearance();
        WygladMur.setTexture(TeksturaMur);
     
        Texture TeksturaBrick = new TextureLoader("obrazki/brick.gif", this).getTexture();
        Appearance WygladBrick = new Appearance();
        WygladBrick.setTexture(TeksturaBrick);
        
        Texture TeksturaTrawka = new TextureLoader("obrazki/trawka.jpg", this).getTexture();
        Appearance WygladTrawka = new Appearance();
        WygladTrawka.setTexture(TeksturaTrawka);
         //==============================
       
         Appearance wyglad_ziemia = new Appearance();
         
          TextureLoader loader = new TextureLoader("obrazki/trawka.jpg",null);
        ImageComponent2D image = loader.getImage();

        Texture2D trawka = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                        image.getWidth(), image.getHeight());

        trawka.setImage(0, image);
        trawka.setBoundaryModeS(Texture.WRAP);
        trawka.setBoundaryModeT(Texture.WRAP);
        
        wyglad_ziemia.setTexture(trawka);
        
        Point3f[]  coords = new Point3f[4];
        for( i = 0; i< 4; i++)
            coords[i] = new Point3f();

        Point2f[]  tex_coords = new Point2f[4];
        for(i = 0; i< 4; i++)
            tex_coords[i] = new Point2f();

        coords[0].y = -0.45f;
        coords[1].y = -0.45f;
        coords[2].y = -0.45f;
        coords[3].y = -0.45f;

        coords[0].x = 3.5f;
        coords[1].x = 3.5f;
        coords[2].x = -3.5f;
        coords[3].x = -3.5f;

        coords[0].z = 3.5f;
        coords[1].z = -3.5f;
        coords[2].z = -3.5f;
        coords[3].z = 3.5f;

        tex_coords[0].x = 0.0f;
        tex_coords[0].y = 0.0f;

        tex_coords[1].x = 10.0f;
        tex_coords[1].y = 0.0f;

        tex_coords[2].x = 0.0f;
        tex_coords[2].y = 10.0f;

        tex_coords[3].x = 10.0f;
        tex_coords[3].y = 10.0f;


        //ziemia

        QuadArray qa_ziemia = new QuadArray(4, GeometryArray.COORDINATES|
                GeometryArray.TEXTURE_COORDINATE_2);
        qa_ziemia.setCoordinates(0,coords);

        qa_ziemia.setTextureCoordinates(0, tex_coords);


        Shape3D ziemia = new Shape3D(qa_ziemia);
        ziemia.setAppearance(wyglad_ziemia);

        wezel_scena.addChild(ziemia);

     //===========================
            Appearance wyglad_ziemia2 = new Appearance();
         
          
        ImageComponent2D image2 = loader.getImage();

        Texture2D trawka2 = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                                        image2.getWidth(), image2.getHeight());

        trawka2.setImage(0, image2);
        trawka2.setBoundaryModeS(Texture.WRAP);
        trawka2.setBoundaryModeT(Texture.WRAP);
        
        wyglad_ziemia2.setTexture(trawka2);
        
        Point3f[]  coords2 = new Point3f[4];
        for( i = 0; i< 4; i++)
            coords2[i] = new Point3f();

        Point2f[]  tex_coords2 = new Point2f[4];
        for(i = 0; i< 4; i++)
            tex_coords2[i] = new Point2f();

        coords2[0].y = -0.46f;
        coords2[1].y = -0.46f;
        coords2[2].y = -0.46f;
        coords2[3].y = -0.46f;

        coords2[0].x = -3.5f;
        coords2[1].x = -3.5f;
        coords2[2].x = 3.5f;
        coords2[3].x = 3.5f;

        coords2[0].z = 3.5f;
        coords2[1].z = -3.5f;
        coords2[2].z = -3.5f;
        coords2[3].z = 3.5f;

        tex_coords2[0].x = 0.0f;
        tex_coords2[0].y = 0.0f;

        tex_coords2[1].x = 10.0f;
        tex_coords2[1].y = 0.0f;

        tex_coords2[2].x = 0.0f;
        tex_coords2[2].y = 10.0f;

        tex_coords2[3].x = 10.0f;
        tex_coords2[3].y = 10.0f;


        //ziemia

        QuadArray qa_ziemia2 = new QuadArray(4, GeometryArray.COORDINATES|
                GeometryArray.TEXTURE_COORDINATE_2);
        qa_ziemia2.setCoordinates(0,coords2);

        qa_ziemia2.setTextureCoordinates(0, tex_coords2);


        Shape3D ziemia2 = new Shape3D(qa_ziemia2);
        ziemia2.setAppearance(wyglad_ziemia2);

        wezel_scena.addChild(ziemia2);

     
        setVisible(true);
        
           return wezel_scena;
        }
        
         CylinderArm(){
           
        super("Pozdrawiamy Doktora Marcina Pazio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));
     
 
        canvas3D.addKeyListener(this);
        add(BorderLayout.CENTER,canvas3D);
        pack();
        setVisible(true);
        BranchGroup scena = utworzScene();
	scena.compile();
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,2.8f));
        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
        OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere());
        simpleU.getViewingPlatform().setViewPlatformBehavior(orbit);
        simpleU.addBranchGraph(scena);
       
 }
     
        
     
public void keyTyped(KeyEvent e)
    {
      
    }
    
    public void keyReleased(KeyEvent e)
    {
       
    }
     
    public void keyPressed(KeyEvent e)
    {
        
        char key = e.getKeyChar();
        
        if (key == 'a')
       {
           if(x1>0.11f)
           {x1-=0.01f;
            p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak.get(macierz2);
            macierz2.m03=(x1)*cos(kx)+macierz2.m23*sin(kx);
            macierz2.m13=y1;
            macierz2.m23=(-x1*sin(kx))+macierz2.m23*cos(kx);
            p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
            p_chwytak.get(macierz);
            p_chwytak.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13,macierz2.m23+macierz.m23));
            obrot_animacja.setTransform(p_chwytak);
            
            p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak1.get(macierz2);
            macierz2.m03=(x1+0.3f)*cos(kx)+macierz2.m23*sin(kx);
            macierz2.m13=y1;
            macierz2.m23=(-(x1+0.3f)*sin(kx))+macierz2.m23*cos(kx);
            p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
            p_chwytak1.get(macierz);
            p_chwytak1.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13+0.05f,macierz2.m23+macierz.m23));
            obrot_animacja1.setTransform(p_chwytak1);
            
            p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak2.get(macierz2);
            macierz2.m03=(x1+0.3f)*cos(kx)+macierz2.m23*sin(kx);
            macierz2.m13=y1;
            macierz2.m23=(-(x1+0.3f)*sin(kx))+macierz2.m23*cos(kx);
            p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
            p_chwytak2.get(macierz);
            p_chwytak2.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13-0.05f,macierz2.m23+macierz.m23));
            obrot_animacja4.setTransform(p_chwytak2);
           }
        }
        if (key == 'd')
       {
           if(x1<0.6f)
           {x1+=0.01f;
        p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak.get(macierz2);
        macierz2.m03=(x1)*cos(kx)+macierz2.m23*sin(kx);
        macierz2.m13=y1;
        macierz2.m23=(-x1*sin(kx))+macierz2.m23*cos(kx);
        p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
        p_chwytak.get(macierz);
        p_chwytak.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13,macierz2.m23+macierz.m23));
        obrot_animacja.setTransform(p_chwytak);
        
        p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak1.get(macierz2);
        macierz2.m03=(x1+0.3f)*cos(kx)+macierz2.m23*sin(kx);
        macierz2.m13=y1;
        macierz2.m23=(-(x1+0.3f)*sin(kx))+macierz2.m23*cos(kx);
        p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
        p_chwytak1.get(macierz);
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13+0.05f,macierz2.m23+macierz.m23));
        obrot_animacja1.setTransform(p_chwytak1);
        
        p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak2.get(macierz2);
        macierz2.m03=(x1+0.3f)*cos(kx)+macierz2.m23*sin(kx);
        macierz2.m13=y1;
        macierz2.m23=(-(x1+0.3f)*sin(kx))+macierz2.m23*cos(kx);
        p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));  
        p_chwytak2.get(macierz);
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03+macierz.m03,macierz2.m13+macierz.m13-0.05f,macierz2.m23+macierz.m23));
        obrot_animacja4.setTransform(p_chwytak2);
           }
        }
       
        if (key == 'w')
       {
           if(y1<0.33f)
           {y1+=0.01f;
           kat=0;
        p_chwytak.get(macierz);
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja.setTransform(p_chwytak);
        
        p_chwytak1.get(macierz);
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13+0.05f,macierz2.m23));
        obrot_animacja1.setTransform(p_chwytak1);

        p_chwytak2.get(macierz);
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13-0.05f,macierz2.m23));
        obrot_animacja4.setTransform(p_chwytak2);


        p_naped.get(macierz);
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_naped.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_naped.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja2.setTransform(p_naped);
            
           }
       }
       
         if (key == 's')
         {
            if(y1>-0.33f)
            {y1-=0.01f;
            kat=0;
             p_chwytak.get(macierz);
            macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
            macierz2.m13=y1;
            macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
            p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
            obrot_animacja.setTransform(p_chwytak);
            
            p_chwytak1.get(macierz);
            macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
            macierz2.m13=y1;
            macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
            p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
            p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13+0.05f,macierz2.m23));
            obrot_animacja1.setTransform(p_chwytak1);

            p_chwytak2.get(macierz);
            macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
            macierz2.m13=y1;
            macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
            p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
            p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13-0.05f,macierz2.m23));
            obrot_animacja4.setTransform(p_chwytak2);


            p_naped.get(macierz);
            macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
            macierz2.m13=y1;
            macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
            p_naped.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
            p_naped.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
            obrot_animacja2.setTransform(p_naped);
                   }
         }
   
         if (key == 'q')
        {
            kat=(float) (Math.PI/32);
        kx=(float) (kx+Math.PI/32);
        tmp_rot.rotY(Math.PI/32);
        
        
        p_chwytak.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak.mul(tmp_rot);
        p_chwytak.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja.setTransform(p_chwytak);
        
        p_chwytak1.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak1.mul(tmp_rot);
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13+0.05f,macierz2.m23));
        obrot_animacja1.setTransform(p_chwytak1);

        p_chwytak2.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak2.mul(tmp_rot);
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13-0.05f,macierz2.m23));
        obrot_animacja4.setTransform(p_chwytak2);

        p_naped.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(kat)+macierz.m23*sin(kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(kat)+macierz.m23*cos(kat);
        p_naped.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_naped.mul(tmp_rot);
        p_naped.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja2.setTransform(p_naped);
        
        
        
        }
        
         if (key == 'e')
        {            
        kat=(float) (Math.PI/32);
        kx=(float) (kx-Math.PI/32);
        tmp_rot.rotY(-kat);
        
        p_chwytak.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(-kat)+macierz.m23*sin(-kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(-kat)+macierz.m23*cos(-kat);
        p_chwytak.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak.mul(tmp_rot);
        p_chwytak.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja.setTransform(p_chwytak);
        
        p_chwytak1.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(-kat)+macierz.m23*sin(-kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(-kat)+macierz.m23*cos(-kat);
        p_chwytak1.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak1.mul(tmp_rot);
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak1.setTranslation(new Vector3d(macierz2.m03,macierz2.m13+0.05f,macierz2.m23));
        obrot_animacja1.setTransform(p_chwytak1);

        p_chwytak2.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(-kat)+macierz.m23*sin(-kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(-kat)+macierz.m23*cos(-kat);
        p_chwytak2.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_chwytak2.mul(tmp_rot);
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        p_chwytak2.setTranslation(new Vector3d(macierz2.m03,macierz2.m13-0.05f,macierz2.m23));
        obrot_animacja4.setTransform(p_chwytak2);


        
        
        p_naped.get(macierz);         // obrot ramieniem chwytaka
        macierz2.m03=macierz.m03*cos(-kat)+macierz.m23*sin(-kat);
        macierz2.m13=y1;
        macierz2.m23=-macierz.m03*sin(-kat)+macierz.m23*cos(-kat);
        p_naped.setTranslation(new Vector3d(0.0f,0.0f,0.0f));
        p_naped.mul(tmp_rot);
        p_naped.setTranslation(new Vector3d(macierz2.m03,macierz2.m13,macierz2.m23));
        obrot_animacja2.setTransform(p_naped);
        } 
           
         
        
    }
     
            
    
    public static void main(String[] args) {
    CylinderArm robot = new CylinderArm() ;
       
 
   robot.addKeyListener(robot);


    }

 }
