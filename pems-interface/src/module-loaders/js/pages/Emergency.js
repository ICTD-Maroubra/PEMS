import React from "react";

import SideMenu from "../components/Menu/SideMenu";

import SingleCard from "../components/Card/SingleCard";
import PageTitle from "../components/Widgets/PageTitle";
import SingleIconCard from "../components/Card/SingleIconCard";



export default class Emergency extends React.Component {

constructor(){
  super();
}

render() {
    const raiseEmergencyIcon = "../../assets/images/edited/RaiseEmergencyCall.png";
    const raiseEmergencyTooltip = "Raise an emergency alarm";

    const currentPath= this.props.location.pathname;
    return (
      <div>
      <SideMenu currentPath={currentPath}/>
      <div class="body">
        <PageTitle title="Emergency"/>
                        <div class="flex-box">
          <SingleIconCard title="Raise Emergency Call" icon={raiseEmergencyIcon} tooltip={raiseEmergencyTooltip}/>
          </div>

</div>
      </div>
    );
  }
}
