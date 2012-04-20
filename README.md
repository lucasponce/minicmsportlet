minicmsportlet
==============

A minimal CMS java portlet.

Copyright (C) 2012 	Lucas Ponce 

Objectives
----------

minicmsportlet is a set of java portlets (based on JSR 168) to offer minimal functionalities of Web Content Management for java portals.

minicmsportlet supports multilanguage content, internal resources repository, in-line editing and clustering cappabilities.

Portlets description:

	- MinimalCMS:		Portlet to edit and view a content from repository.
	- ImagesCMS:		Portlet to upload and admin images or other resouces into repository.
	- ContentListCMS:	Portlet to view and admin content stored in repository.

Requeriments
------------

minicmsportlet uses standard java portlets, so it can be migrated easily to any standard PortletContainer based on JSR 168 or JSR 286 standards.

minicmsportlet uses JBoss Cache for internal repository with clustering cappabilities.

minicmsportlet has been tested with JBoss Enterprise Portal Platform 5.2 based on GateIn with "all" type instance.

You can get JBoss EPP 5.2 from here:	

	https://access.redhat.com/downloads/evals

Installation
------------

Steps:

	- [1]	Update build.properties with the path of your jboss-epp-5.2 home and with your "all" based instance name.
	- [2]	ant install to compile and deploy minicmslib.jar into $INSTANCE/lib folder and minicmsportlet.war into $INSTANCE/deploy folder.
	- [3]	Start your instance
	- [4]	Take a look into minitutorial about how to create new portal pages with MinimalCMS portlets and start editing web content.

Configuration
-------------

minicmsportlet uses JBoss Cache as repository, and it's configured into the following path minicmsportlet.war/WEB-INF/minicms-jboss-beans.xml.

JBoss Cache is configured to use a local folder as cache loader, this folder can be configured with a java system property 

	-Dminicms.data.dir=<path to your folder repository>

minicmsportlet is a really simple piece of code, you can easily adapt it to your needs.

JBoss Cache is not mandatory, you can rewrite your own repository just adding an implementation of the following interfaces:

	- content.ContentAPI
	- images.ImagesAPI

You can take:

	- content.CacheContentImpl and 
	- images.CacheImagesImpl 

as examples of implementations but the aim of this design is to add different implementations in a simple way.

Tutorial
--------

In the minitutorial folder there are several screenshots showing how to use the content portlets.

Feedback
--------

Please, feel free to give feedback for future modifications.

https://github.com/lucasponce/minicmsportlet

ponce.ballesteros@gmail.com

Thanks !







	


