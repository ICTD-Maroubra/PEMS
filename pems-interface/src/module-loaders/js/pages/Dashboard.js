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
          labels: ['R1', 'R2', 'R3', 'R4', 'R5', 'R6'],
          datasets: [
                      {
                        label:"temp data",
                        data:[
                              7,
                              2,
                              3,
                              6,
                              5,
                              7
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

        <TripleCards title1= "Counter 1" content1="13"   unit1="°C" accId1="tab-one" accContent1="Content ONE"
                     title2= "Counter 2" content2="23"   unit2="%" accId2="tab-two" accContent2="Content TWO"
                     title3= "Counter 3" content3="20" unit3="°C" accId3="tab-three" accContent3="Content THREE"/>

        <div class="flex-box">
        <SingleCard title= "Temperature Graph" content={InlineChart} unit="" accId="tab-six" accContent="Content SIX"/>
        </div>

        <DoubleCards title1= "Counter 4" content1="99"   unit1="°C" accId1="tab-four" accContent1="Content FOUR"
                     title2= "Counter 5" content2="100" unit2="%" accId2="tab-five" accContent2="Content FIVE"/>

</div>
      </div>
    );
  }
}
