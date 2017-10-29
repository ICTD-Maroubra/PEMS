import React from "react";

import SideMenu from "../components/Menu/SideMenu";

import SingleCard from "../components/Card/SingleCard";
import DoubleCards from "../components/Card/DoubleCards";
import TripleCards from "../components/Card/TripleCards";
import PageTitle from "../components/Widgets/PageTitle";
import LineChart from "../components/Charts/LineChart";

import Alarms from "../components/Alarms/Alarms";


export default class Dashboard extends React.Component {

  constructor(){
    super();
    this.state = {
      chartData:{},
      test: []
    };
    }

    componentWillMount(){
      this.getChartData();
      this.APITest();
    }

    APITest() {
    fetch('https://pems-server.herokuapp.com/api-browser/#/definitions/HelloWorldResponse')
        .then(({ results }) => this.setState({ test: results }));
    }

  getChartData(){
    //AJAX CALL HERE
    this.setState({
      chartData:{
          labels: ['12:30', '13:00', '13:30', '14:00', '14:30', '15:00','15:30', '16:00', '16:30', '17:00', '17:30', '18:00'],
          datasets: [
                      {
                        label:"temp data",
                        data:[
                              17,
                              20,
                              23,
                              26,
                              25,
                              27,
                              23,
                              20,
                              23,
                              26,
                              25,
                              27
                              ],
                        borderColor: [
                        'rgb(78, 94, 106)'
                        ]
                      }
                    ]
      }
    });
  }

render() {

  const InlineChart = [
    <div><LineChart chartData={this.state.chartData} /></div>
  ]

  const currentPath= this.props.location.pathname;
  return (
    <div>
    <SideMenu currentPath={currentPath}/>
      <div class="body">
        <PageTitle title="Dashboard"/>

        <div class="alarm-accordion">
        <Alarms id="tab-seven" content=""/>
        </div>

        <TripleCards title1= "Oxygen Level" content1="19.2"   unit1="%" accId1="tab-one" accContent1="Oxygen sensor description"
                     title2= "Temperature" content2="23"   unit2="°C" accId2="tab-two" accContent2="Current temperature"
                     title3= "Humidity" content3="45" unit3="%" accId3="tab-three" accContent3="Humidity sensor description"/>

        <div class="flex-box">
        <SingleCard title= "Temperature Graph" content={InlineChart} unit="" accId="tab-six" accContent="Temperature Readings"/>
        </div>

        <DoubleCards title1= "CO2" content1="389"   unit1="ppm" accId1="tab-four" accContent1="CO2 sensor description"
                     title2= "Luminosity" content2="450" unit2="nm" accId2="tab-five" accContent2="Luminosity sensor description"/>

</div>
      </div>
    );
  }
}
