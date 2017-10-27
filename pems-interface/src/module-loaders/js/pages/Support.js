import React from "react";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import PageTitle from "../components/Widgets/PageTitle";

import SingleIconCard from "../components/Card/SingleIconCard";
import DoubleIconCard from "../components/Card/DoubleIconCard";

export default class Support extends React.Component {

constructor(){
  super();
}

render() {
  const supportIcon = "../../assets/images/edited/HelpIcon.png";
    return (
      <div>
        <PageTitle title="Support"/>

          <DoubleIconCard title1= "support" icon1={supportIcon}  title2= "Counter 2" icon2={supportIcon}/>


          <SingleIconCard title="Documentation" icon={supportIcon} />
      </div>
    );
  }
}
