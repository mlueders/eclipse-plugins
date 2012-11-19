See feature/Plugins.txt for (somewhat dated) general overview of plugins

Unzip plugins.zip into $ECLIPSE_HOME/dropins/eclipse

To generate, open feature/feature.xml
Overview > Export Wizard
Under Options tab, ensure *only* the following are selected 
 - Export Source (Generate source bundles)
 - Allow for binary cycles
 - Use class files compiled in workspace
Select Destination and Finish
