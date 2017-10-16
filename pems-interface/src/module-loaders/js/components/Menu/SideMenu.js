import React from "react";

import MenuPlaceholder from "./MenuPlaceholder";

export default class SideMenu extends React.Component {

  render() {

    const menuItemOne = "Dashboard";
    const menuItemTwo = "Emergency";
    const menuItemThree = "Support";
    const menuItemFour = "Users";
    const menuItemOneIcon = "../../assets/images/edited/dashboardIcon.png";
    const menuItemTwoIcon = "../../assets/images/edited/EmergencyIcon.png";
    const menuItemThreeIcon = "../../assets/images/edited/HelpIcon.png";
    const menuItemFourIcon = "../../assets/images/edited/UsersIcon.png";

    return (
      <div class="Menu">

      <MenuPlaceholder title={menuItemOne} icon={menuItemOneIcon}/>
      <MenuPlaceholder title={menuItemTwo} icon={menuItemTwoIcon}/>
      <MenuPlaceholder title={menuItemThree} icon={menuItemThreeIcon}/>
      <MenuPlaceholder title={menuItemFour} icon={menuItemFourIcon}/>

      </div>
    );
  }
}
