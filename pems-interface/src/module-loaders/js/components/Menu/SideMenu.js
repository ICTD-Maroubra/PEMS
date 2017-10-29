import React from "react";

import MenuPlaceholder from "./MenuPlaceholder";

export default class SideMenu extends React.Component {

  constructor() {
    super();

  }

  render() {

    const menuItemOne = "Dashboard";
    const menuItemTwo = "Emergency";
    const menuItemThree = "Support";
    const menuItemFour = "Users";
    const menuItemFive = "Settings";
    const menuItemOneIcon = "../../assets/images/edited/dashboardIcon.png";
    const menuItemTwoIcon = "../../assets/images/edited/EmergencyIcon.png";
    const menuItemThreeIcon = "../../assets/images/edited/HelpIcon.png";
    const menuItemFourIcon = "../../assets/images/edited/UsersIcon.png";
    const menuItemFiveIcon = "../../assets/images/edited/SettingsIcon.png";


    const dashboardClass = this.props.currentPath.match(/^\/Dashboard/) || this.props.currentPath === "/"  ? "activeMenuItem" : "MenuItem";
    const emergencyClass = this.props.currentPath.match(/^\/Emergency/) ? "activeMenuItem" : "MenuItem";
    const supportClass = this.props.currentPath.match(/^\/Support/) ? "activeMenuItem" : "MenuItem";
    const settingsClass = this.props.currentPath.match(/^\/Settings/) ? "activeMenuItem" : "MenuItem";
    const usersClass = this.props.currentPath.match(/^\/Users/) ? "activeMenuItem" : "MenuItem";

    return (

      <div class="Menu">

      <MenuPlaceholder title={menuItemOne} icon={menuItemOneIcon} className={dashboardClass}/>
      <MenuPlaceholder title={menuItemTwo} icon={menuItemTwoIcon} className={emergencyClass}/>
      <MenuPlaceholder title={menuItemThree} icon={menuItemThreeIcon} className={supportClass}/>
      <MenuPlaceholder title={menuItemFive} icon={menuItemFiveIcon} className={settingsClass}/>

      </div>
    );
  }
}
