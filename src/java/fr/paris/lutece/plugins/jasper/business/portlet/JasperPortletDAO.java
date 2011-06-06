/*
 * Copyright (c) 2002-2011, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.jasper.business.portlet;

import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * this class provides Data Access methods for JasperPortlet objects
 */
public final class JasperPortletDAO implements IJasperPortletDAO
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_portlet, jasper_feed_id FROM jasper_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO jasper_portlet ( id_portlet, jasper_feed_id ) VALUES ( ?, ? )";
    private static final String SQL_QUERY_DELETE = "DELETE FROM jasper_portlet WHERE id_portlet = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE jasper_portlet SET id_portlet = ?, jasper_feed_id = ? WHERE id_portlet = ? ";
    private static final String SQL_QUERY_CHECK_PORTLET_LINKED = "SELECT jasper_feed_id FROM jasper_portlet WHERE jasper_feed_id = ? ";

    ///////////////////////////////////////////////////////////////////////////////////////
    // Access methods to data

    /**
     * Insert a new record in the table.
     *
     * @param portlet The Instance of the Portlet
     */
    public void insert( Portlet portlet )
    {
        JasperPortlet p = (JasperPortlet) portlet;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );
        daoUtil.setInt( 1, p.getId(  ) );
        daoUtil.setString( 2, p.getJasperFeedId(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Delete record from table
     *
     * @param nPortletId The indentifier of the Portlet
     */
    public void delete( int nPortletId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );
        daoUtil.setInt( 1, nPortletId );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * Update the record in the table
     *
     * @param portlet The reference of the portlet
     */
    public void store( Portlet portlet )
    {
        JasperPortlet p = (JasperPortlet) portlet;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE );
        daoUtil.setInt( 1, p.getId(  ) );
        daoUtil.setString( 2, p.getJasperFeedId(  ) );
        daoUtil.setInt( 3, p.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * load the data of dbpagePortlet from the table
     * @return portlet The instance of the object portlet
     * @param nIdPortlet The identifier of the portlet
     */
    public Portlet load( int nIdPortlet )
    {
        JasperPortlet portlet = new JasperPortlet(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT );
        daoUtil.setInt( 1, nIdPortlet );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            portlet.setId( daoUtil.getInt( 1 ) );
            portlet.setJasperFeedId( daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return portlet;
    }

    /**
     * Checks if a feed is linked to a portlet
     * @return A boolean
     * @param nIdJasperFeed The identifier of the Jasper feed
     */
    public boolean checkNoPortletLinked( int nIdJasperFeed )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CHECK_PORTLET_LINKED );
        daoUtil.setInt( 1, nIdJasperFeed );
        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            daoUtil.free(  );

            return false;
        }

        daoUtil.free(  );

        return true;
    }
}
