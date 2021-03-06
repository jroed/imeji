/**
 * License: src/main/resources/license/escidoc.license
 */

package de.mpg.imeji.presentation.util;

import java.net.URI;

import de.mpg.imeji.logic.vo.MetadataProfile;
import de.mpg.imeji.presentation.beans.SessionBean;

/**
 * If an object is already in a session, return it.
 * <br/> Increase performance compared to {@link ObjectLoader}
 * @author saquet
 *
 */
public class ObjectCachedLoader
{
	public static MetadataProfile loadProfile(URI uri)
	{
		SessionBean sessionBean = (SessionBean) BeanHelper.getSessionBean(SessionBean.class);
		MetadataProfile profile = sessionBean.getProfileCached().get(uri);
		if (profile == null)
		{
			profile = ObjectLoader.loadProfile(uri, sessionBean.getUser());
			sessionBean.getProfileCached().put(profile.getId(), profile);
		}
		return profile;
	}
	
}
