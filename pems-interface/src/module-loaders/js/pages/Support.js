import React from "react";

import SideMenu from "../components/Menu/SideMenu";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import PageTitle from "../components/Widgets/PageTitle";

import SingleIconCard from "../components/Card/SingleIconCard";
import DoubleIconCard from "../components/Card/DoubleIconCard";
import DoubleIconCard2 from "../components/Card/DoubleIconCard2";

export default class Support extends React.Component {

constructor(){
  super();
}

render() {
  const raiseTicketIcon = "../../assets/images/edited/RaiseTicket.png";
  const raiseTicketTooltip = "Raise a ticket with our administrators";

  const forumsIcon = "../../assets/images/edited/Forums.png";
  const forumsTooltip = "View our forums";

  const faqsIcon = "../../assets/images/edited/FAQs.png";
  const faqsTooltip = "Check the FAQs for your enquiry";

  const documentationIcon = "../../assets/images/edited/Documentation.png";
  const documentationTooltip = "Read our documentation";

  const currentPath= this.props.location.pathname;
  return (
    <div>
    <SideMenu currentPath={currentPath}/>
      <div class="body">
        <PageTitle title="Support"/>

          <DoubleIconCard title1= "Raise Ticket" icon1={raiseTicketIcon} tooltip1={raiseTicketTooltip} title2= "Forums" icon2={forumsIcon} tooltip2={forumsTooltip}/>
          <DoubleIconCard2 title1= "FAQs" icon1={faqsIcon} tooltip1={faqsTooltip} title2= "Documentation" icon2={documentationIcon} tooltip2={documentationTooltip}/>
</div>
      </div>
    );
  }
}
