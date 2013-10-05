package com.swtdesigner;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;

/**
 * Utility class for managing resources such as colors, fonts, images, etc.
 * 
 * This class may be freely distributed as part of any application or plugin.
 * <p>
 * Copyright (c) 2003 - 2004, Instantiations, Inc. <br>All Rights Reserved
 * 
 * @author scheglov_ke
 */
public class SwingResourceManager {
	
	/**
	 * Maps image names to images
	 */
	private static HashMap<String, Image> m_ClassImageMap = new HashMap<String, Image>();
	
    /**
     * Returns an image encoded by the specified input stream
     * @param is InputStream The input stream encoding the image data
     * @return Image The image encoded by the specified input stream
     */
	private static Image getImage(final InputStream is) {
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final byte buf[] = new byte[1024 * 4];
			while (true) {
				final int n = is.read(buf);
				if (n == -1)
					break;
				baos.write(buf, 0, n);
			}
			baos.close();
			return Toolkit.getDefaultToolkit().createImage(baos.toByteArray());
		} catch (final Throwable e) {
			return null;
		}
	}
	
    /**
     * Returns an image stored in the file at the specified path relative to the specified class
     * @param clazz Class The class relative to which to find the image
     * @param path String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
	public static Image getImage(final Class<?> clazz, final String path) {
		final String key = clazz.getName() + '|' + path;
		Image image = SwingResourceManager.m_ClassImageMap.get(key);
		if (image == null) {
			if ((path.length() > 0) && (path.charAt(0) == '/')) {
				final String newPath = path.substring(1, path.length());
				image = SwingResourceManager.getImage(new BufferedInputStream(clazz.getClassLoader().getResourceAsStream(newPath)));
			} else {
				image = SwingResourceManager.getImage(clazz.getResourceAsStream(path));
			}
			SwingResourceManager.m_ClassImageMap.put(key, image);
		}
		return image;
	}
	
    /**
     * Returns an image stored in the file at the specified path
     * @param path String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
	public static Image getImage(final String path) {
		return SwingResourceManager.getImage("default", path); //$NON-NLS-1$
	}
	
    /**
     * Returns an image stored in the file at the specified path
     * @param section String The storage section in the cache
     * @param path String The path to the image file
     * @return Image The image stored in the file at the specified path
     */
	public static Image getImage(final String section, final String path) {
		final String key = section + '|' + SwingResourceManager.class.getName() + '|' + path;
		Image image = SwingResourceManager.m_ClassImageMap.get(key);
		if (image == null) {
			try {
				final FileInputStream fis = new FileInputStream(path);
				image = SwingResourceManager.getImage(fis);
				SwingResourceManager.m_ClassImageMap.put(key, image);
				fis.close();
			} catch (final IOException e) {
				return null;
			}
		}
		return image;
	}
	
    /**
	 * Clear cached images in specified section
	 * @param section the section do clear
	 */
	public static void clearImages(final String section) {
		for (final Iterator<String> I = SwingResourceManager.m_ClassImageMap.keySet().iterator(); I.hasNext();) {
			final String key = I.next();
			if (!key.startsWith(section + '|'))
				continue;
			final Image image = SwingResourceManager.m_ClassImageMap.get(key);
			image.flush();
			I.remove();
		}
	}
	
    /**
     * Returns an icon stored in the file at the specified path relative to the specified class
     * @param clazz Class The class relative to which to find the icon
     * @param path String The path to the icon file
     * @return Icon The icon stored in the file at the specified path
     */
	public static ImageIcon getIcon(final Class<?> clazz, final String path) {
		return SwingResourceManager.getIcon(SwingResourceManager.getImage(clazz, path));
	}
	
    /**
     * Returns an icon stored in the file at the specified path
     * @param path String The path to the icon file
     * @return Icon The icon stored in the file at the specified path
     */
	public static ImageIcon getIcon(final String path) {
		return SwingResourceManager.getIcon("default", path); //$NON-NLS-1$
	}
	
    /**
     * Returns an icon stored in the file at the specified path
     * @param section String The storage section in the cache
     * @param path String The path to the icon file
     * @return Icon The icon stored in the file at the specified path
     */
	public static ImageIcon getIcon(final String section, final String path) {
		return SwingResourceManager.getIcon(SwingResourceManager.getImage(section, path));
	}

    /**
     * Returns an icon based on the specified image
     * @param image Image The original image
     * @return Icon The icon based on the image
     */
	public static ImageIcon getIcon(final Image image) {
		if (image == null)
			return null;
		return new ImageIcon(image);
	}
}